package com.osamaaftab.teya.data.source.remote

import com.osamaaftab.teya.domain.model.ResponseModel
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

interface AlbumServices {
    @GET("us/rss/topalbums/limit={limit}/json")
    fun getAlbumAsync(
        @Path("limit") limit: Int
    ): Deferred<ResponseModel>
}