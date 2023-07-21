package com.osamaaftab.teya.di.module

import com.osamaaftab.teya.data.ApiErrorHandle
import com.osamaaftab.teya.domain.repository.AlbumRepository
import com.osamaaftab.teya.domain.usecase.GetAlbumUseCase
import org.koin.dsl.module


val UseCaseModule = module {
    single { provideGetAlbumUseCase(get(), get()) }
}

private fun provideGetAlbumUseCase(
    questionRepository: AlbumRepository,
    apiErrorHandle: ApiErrorHandle
): GetAlbumUseCase {
    return GetAlbumUseCase(questionRepository, apiErrorHandle)
}