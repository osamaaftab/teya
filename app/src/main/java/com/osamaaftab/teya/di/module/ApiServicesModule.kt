package com.osamaaftab.teya.di.module

import com.osamaaftab.teya.data.source.remote.AlbumServices
import org.koin.dsl.module
import retrofit2.Retrofit

val ApiServicesModule = module {
    single { provideAlbumService(get()) }
}

private fun provideAlbumService(retrofit: Retrofit): AlbumServices {
    return retrofit.create(AlbumServices::class.java)
}