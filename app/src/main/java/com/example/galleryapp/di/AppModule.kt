package com.example.galleryapp.di

import com.example.galleryapp.database.provideDatabase
import com.example.galleryapp.database.provideStationDao
import com.example.galleryapp.database.provideStationDetailsDao
import com.example.galleryapp.network.provideApiService
import com.example.galleryapp.network.provideRetrofit
import com.example.galleryapp.repository.DetailsRepository
import com.example.galleryapp.repository.RadioListRepository
import com.example.galleryapp.viewmodel.DetailsViewModel
import com.example.galleryapp.viewmodel.RadioListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // Retrofit and ApiService
    single { provideRetrofit() }
    single { provideApiService(get()) }

    // Room Database and DAO
    single { provideDatabase(androidContext()) }
    single { provideStationDao(get()) }
    single { provideStationDetailsDao(get()) }

    // Repositories
    single { RadioListRepository(apiService = get(), stationDao = get(), context = androidContext()) }
    factory { DetailsRepository(apiService = get(), stationDetailsDao = get(), context = androidContext()) }

    // ViewModels
    viewModel { RadioListViewModel(radioListRepository = get()) }
    viewModel { (stationId: String) -> DetailsViewModel(detailsRepository = get(), stationId = stationId) }
}