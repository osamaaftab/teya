package com.osamaaftab.teya.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FeedModel(
    val entry: List<EntryModel>,
) : Parcelable