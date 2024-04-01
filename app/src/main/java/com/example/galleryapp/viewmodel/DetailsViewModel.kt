package com.example.galleryapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.galleryapp.core.UiState
import com.example.galleryapp.model.StationDetails
import com.example.galleryapp.repository.DetailsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DetailsViewModel(private val detailsRepository: DetailsRepository, private val stationId: String) : ViewModel() {
    private val _stationDetails = MutableStateFlow<UiState<StationDetails?>>(UiState.Loading)
    val stationDetails = _stationDetails.asStateFlow()

    init {
        loadStationDetails()
    }

    private fun loadStationDetails() = viewModelScope.launch {
        _stationDetails.emit(UiState.Loading)
        try {
            detailsRepository.fetchStationDetailsById(stationId).collectLatest { detailsEntity ->
                _stationDetails.emit(UiState.Success(detailsEntity))
            }
        } catch (e: Exception) {
            _stationDetails.emit(UiState.Error(e))
        }
    }
}

