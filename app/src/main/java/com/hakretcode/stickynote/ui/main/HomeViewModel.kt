package com.hakretcode.stickynote.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hakretcode.stickynote.domain.NoteInteractor
import com.hakretcode.stickynote.data.model.Note
import com.hakretcode.stickynote.data.model.Resource
import com.hakretcode.stickynote.util.HCLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val domain: NoteInteractor
) : ViewModel() {
    private val _liveData = HCLiveData<Resource<List<Note>>>()
    val liveData: LiveData<Resource<List<Note>>> = _liveData

    fun getAllNotes() {
        viewModelScope.launch {
            domain.getNotes { _liveData.postValue(it) }
        }
    }
}