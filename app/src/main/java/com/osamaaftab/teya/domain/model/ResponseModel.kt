package com.osamaaftab.teya.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResponseModel(
    val feed: FeedModel,
) : Parcelable