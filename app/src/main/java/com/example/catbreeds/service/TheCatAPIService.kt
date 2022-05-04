package com.example.catbreeds.service

import com.example.catbreeds.model.Cat
import com.example.catbreeds.model.Image
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class TheCatAPIService {

    private val baseUrl = "https://api.thecatapi.com/"
    private val api = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(TheCatAPI::class.java)

    fun getBreedDataFromAPI(): Single<List<Cat>> {
        return api.getBreedsFromAPI()
    }

    fun searchBreedDataFromAPI(catBreed: String): Single<List<Cat>> {
        return api.searchBreedFromAPI(catBreed)
    }

    fun getImageDataFromAPI(imageId: String): Single<Image> {
        return api.getImageFromAPI(imageId)
    }
}