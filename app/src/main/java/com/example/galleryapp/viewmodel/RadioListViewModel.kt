package com.example.galleryapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.galleryapp.core.UiState
import com.example.galleryapp.model.RadioStation
import com.example.galleryapp.repository.RadioListRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RadioListViewModel(private val radioListRepository: RadioListRepository) : ViewModel() {
    private val _stations = MutableStateFlow<UiState<List<RadioStation>>>(UiState.Loading)
    val stations: StateFlow<UiState<List<RadioStation>>> = _stations

    init {
        // Future Enhancement: Implement pagination using the Paging 3 library for scalable data loading.
        // This involves updates throughout the data flow, from the repository to the ViewModel and the UI layer,
        // and corresponding adjustments for data retrieval and display.
        getStations("STATIONS_TOP", 100, 0)
    }

    private fun getStations(systemName: String, count: Int, offset: Int) = viewModelScope.launch {
        _stations.emit(UiState.Loading)
        try {
            radioListRepository.getStations(systemName, count, offset).collect { stationsList ->
                _stations.emit(UiState.Success(stationsList))
            }
        } catch (e: Exception) {
            _stations.emit(UiState.Error(e))
        }
    }
}


