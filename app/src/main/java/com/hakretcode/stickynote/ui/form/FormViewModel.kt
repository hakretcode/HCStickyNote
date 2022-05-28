package com.hakretcode.stickynote.ui.form

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hakretcode.stickynote.data.model.Note
import com.hakretcode.stickynote.data.model.Resource
import com.hakretcode.stickynote.data.repositories.NoteRepository
import com.hakretcode.stickynote.domain.NoteInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FormViewModel @Inject constructor(
    private val interactor: NoteInteractor
    ) : ViewModel() {
    var note: Note? = null
    private val _liveData = MutableLiveData<Resource<Nothing>>()
    val liveData: LiveData<Resource<Nothing>> = _liveData

    fun saveNote(title: String?, body: String?) {
        viewModelScope.launch {
            interactor.saveNotes(note, title, body) { _liveData.postValue(it) }
        }
    }
}
