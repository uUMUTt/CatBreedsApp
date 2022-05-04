package com.example.catbreeds.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.catbreeds.model.Cat

class FavoriteBreedDetailViewModel(application: Application) : BaseViewModel(application) {
    val catLiveData = MutableLiveData<Cat>()
}