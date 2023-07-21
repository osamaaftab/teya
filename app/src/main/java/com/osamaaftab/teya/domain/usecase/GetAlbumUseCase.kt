package com.osamaaftab.teya.domain.usecase

import com.osamaaftab.teya.data.ApiErrorHandle
import com.osamaaftab.teya.domain.model.FeedModel
import com.osamaaftab.teya.domain.repository.AlbumRepository
import com.osamaaftab.teya.domain.usecase.base.UseCase

class GetAlbumUseCase constructor(
    private val albumRepository: AlbumRepository,
    apiErrorHandle: ApiErrorHandle?
) : UseCase<FeedModel, Int>(apiErrorHandle) {

    override suspend fun run(params: Int?): FeedModel {
        if (params == null) {
            throw IllegalArgumentException("Param must not be null")
        }
        return albumRepository.getAlbum(params).feed
    }
}
