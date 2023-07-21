package com.osamaaftab.teya.data.repository

import com.osamaaftab.teya.data.source.remote.AlbumServices
import com.osamaaftab.teya.domain.model.ResponseModel
import com.osamaaftab.teya.domain.repository.AlbumRepository

class AlbumRepositoryImp(private val albumServices: AlbumServices) : AlbumRepository {
    override suspend fun getAlbum(limit: Int): ResponseModel {
        return albumServices.getAlbumAsync(limit).await()
    }
}