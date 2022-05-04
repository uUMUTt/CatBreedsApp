package com.example.catbreeds.model

import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("height")
    val height: String?,
    @SerializedName("id")
    val imageId: String?,
    @SerializedName("url")
    val imageUrl: String?,
    @SerializedName("width")
    val width: String?
)
