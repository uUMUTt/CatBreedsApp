package com.example.catbreeds.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.catbreeds.model.Cat
import com.example.catbreeds.service.CatDatabase
import kotlinx.coroutines.launch

class FavoriteListViewModel(application: Application) : BaseViewModel(application) {
    val favoriteList = MutableLiveData<List<Cat>>()
    val isBlankList = MutableLiveData<Boolean>()
    private val dao = CatDatabase(getApplication()).catDao()

    fun getDataFromDB() {
        launch {
            favoriteList.value = dao.getAllFavoriteCatBreeds()
        }

    }

    fun isBlankFavoriteList() {
        isBlankList.value = true
        if (favoriteList.value != null) {
            if (favoriteList.value!!.isNotEmpty()) {
                isBlankList.value = false
            }
        }
    }

    fun deleteFavoriteFromDB(apiId: String?) {
        launch {
            apiId?.let { dao.deleteFavoriteCatBreed(it) }
        }
    }
}