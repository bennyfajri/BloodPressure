package com.beni.bloodpressure.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.beni.core.data.SampleRepository
import com.beni.core.data.local.room.PatientEntitiy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: SampleRepository
) : ViewModel() {

    fun getAllData() = repository.getAllData().asLiveData()

    fun insertMultipleData(patients: List<PatientEntitiy>) = viewModelScope.launch {
        repository.insertMultipleData(patients)
    }

    fun deleteALlData() = viewModelScope.launch {
        repository.removeAllItem()
    }

}