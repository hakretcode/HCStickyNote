package com.hakretcode.stickynote.data.repositories

import com.hakretcode.stickynote.data.db.NoteDao
import com.hakretcode.stickynote.data.model.Note
import com.hakretcode.stickynote.data.model.Resource
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class NoteRepository @Inject constructor(
    private val noteDao: NoteDao,
    private val coroutineContext: CoroutineContext
    ) {
    suspend fun fetchAllNotes(callback: (List<Note>) -> Unit) =
        withContext(coroutineContext) { callback(noteDao.fetchAllNotes()) }

    suspend fun saveNote(note: Note, callback: () -> Unit) = withContext(coroutineContext) {
        noteDao.save(note)
        callback()
    }
}
