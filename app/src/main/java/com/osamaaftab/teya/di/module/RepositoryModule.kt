package com.osamaaftab.teya.di.module

import com.osamaaftab.teya.data.repository.AlbumRepositoryImp
import com.osamaaftab.teya.data.source.remote.AlbumServices
import com.osamaaftab.teya.domain.repository.AlbumRepository
import org.koin.dsl.module

val RepositoryModule = module {
    single { provideAlbumRepository(get()) }
}

fun provideAlbumRepository(albumServices: AlbumServices): AlbumRepository {
    return AlbumRepositoryImp(albumServices)
}