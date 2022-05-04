package com.example.catbreeds.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(indices = [Index(value = ["id"], unique = true)])
data class Cat(
    @ColumnInfo(name = "id")
    @SerializedName("id")
    val apiID: String?,
    @ColumnInfo(name = "name")
    @SerializedName("name")
    val breed: String?,
    @ColumnInfo(name = "origin")
    @SerializedName("origin")
    val origin: String?,
    @ColumnInfo(name = "life_span")
    @SerializedName("life_span")
    val lifeSpan: String?,
    @ColumnInfo(name = "description")
    @SerializedName("description")
    val detail: String?,
    @ColumnInfo(name = "reference_image_id")
    @SerializedName("reference_image_id")
    val imageId: String?,
    @ColumnInfo(name = "wikipedia_url")
    @SerializedName("wikipedia_url")
    val wikipediaLink: String?,
    @SerializedName("isFavorite")
    var isFavorite: Boolean = false,
    @SerializedName("imageUrl")
    var imageUrl: String?
) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var breedID: Int = 0
}