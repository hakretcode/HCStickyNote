package com.hakretcode.stickynote.domain

import com.hakretcode.stickynote.data.model.Note
import com.hakretcode.stickynote.data.model.Resource
import com.hakretcode.stickynote.data.repositories.NoteRepository
import javax.inject.Inject

class NoteInteractor @Inject constructor(private val repository: NoteRepository) {
    suspend fun getNotes(callback: (Resource<List<Note>>) -> Unit) {
        callback(Resource.Loading())
        repository.fetchAllNotes { notes ->
            callback(if (notes.isNotEmpty()) Resource.Success(notes)
            else Resource.Empty())
            callback(Resource.Complete())
        }
    }

    suspend fun saveNotes(note: Note?, title: String?, body: String?, callback: (Resource<Nothing>) -> Unit) {
        if (body.isNullOrEmpty()) callback(Resource.Error("Write something"))
        else {
            val noteNewInstance = Note(note?.id, title, body)
            repository.saveNote(noteNewInstance) { callback(Resource.Success()) }
        }
    }
}