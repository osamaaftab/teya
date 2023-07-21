package com.osamaaftab.teya.domain.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class EntryModel(
    @field:Json(name="im:image")
    val imageList: List<EntryImage>,
    @field:Json(name="im:name")
    val name: EntryName,
    val title: EntryName,
    @field:Json(name="im:artist")
    val artist: EntryName,
    @field:Json(name = "im:price")
    val price : EntryName,
    val id : EntryId
) : Parcelable

@Parcelize
data class EntryName(
    val label: String,
) : Parcelable

@Parcelize
data class EntryId(
    val label: String,
) : Parcelable

@Parcelize
data class EntryImage(
    val label: String,
    val attributes: ImageAttribute,
) : Parcelable

@Parcelize
data class ImageAttribute(
    val height: String,
) : Parcelable