package com.example.catbreeds.service

import com.example.catbreeds.model.Cat
import com.example.catbreeds.model.Image
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheCatAPI {

    @GET("v1/breeds")
    fun getBreedsFromAPI(): Single<List<Cat>>


    @GET("v1/breeds/search")
    fun searchBreedFromAPI(@Query("q") catBreed: String?): Single<List<Cat>>


    @GET("v1/images/{image_id}")
    fun getImageFromAPI(@Path("image_id") imageId: String?): Single<Image>
}