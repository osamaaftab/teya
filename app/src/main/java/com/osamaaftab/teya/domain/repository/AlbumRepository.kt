package com.osamaaftab.teya.domain.repository

import com.osamaaftab.teya.domain.model.ResponseModel

interface AlbumRepository {
    suspend fun getAlbum(limit: Int): ResponseModel
}