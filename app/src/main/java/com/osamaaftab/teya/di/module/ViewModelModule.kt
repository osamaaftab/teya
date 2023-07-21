package com.osamaaftab.teya.di.module

import com.osamaaftab.teya.presentation.viewmodel.AlbumViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val ActivityModule = module {
    viewModel { AlbumViewModel(get()) }
}