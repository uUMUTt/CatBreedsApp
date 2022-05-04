package com.example.catbreeds.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.catbreeds.model.Cat


class BreedDetailViewModel(application: Application) : BaseViewModel(application) {
    var catLiveData = MutableLiveData<Cat>()
}