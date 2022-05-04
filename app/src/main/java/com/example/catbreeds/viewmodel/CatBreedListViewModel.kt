package com.example.catbreeds.viewmodel

import android.app.Application
import android.widget.ImageView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.lifecycle.MutableLiveData
import com.example.catbreeds.model.Cat
import com.example.catbreeds.model.Image
import com.example.catbreeds.service.CatDatabase
import com.example.catbreeds.service.TheCatAPIService
import com.example.catbreeds.util.addImageWithGlide
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import java.lang.Exception

class CatBreedListViewModel(application: Application) : BaseViewModel(application) {
    val breedList = MutableLiveData<List<Cat>>()
    val isError = MutableLiveData<Boolean>()
    val isLoading = MutableLiveData<Boolean>()

    private val theCatAPIService = TheCatAPIService()
    private val disposable = CompositeDisposable()

    private val dao = CatDatabase(getApplication()).catDao()

    fun getDataFromAPI() {
        isLoading.value = true

        disposable.addAll(
            theCatAPIService.getBreedDataFromAPI()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Cat>>() {
                    override fun onSuccess(list: List<Cat>) {
                        breedList.value = list
                        isError.value = false
                        isLoading.value = false
                    }

                    override fun onError(e: Throwable) {
                        isError.value = true
                        isLoading.value = false
                        e.printStackTrace()
                    }

                })
        )
    }

    fun searchDataInAPI(catBreed: String) {
        isLoading.value = true
        disposable.addAll(
            theCatAPIService.searchBreedDataFromAPI(catBreed)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Cat>>() {
                    override fun onSuccess(list: List<Cat>) {
                        breedList.value = list
                        isError.value = false
                        isLoading.value = false
                    }

                    override fun onError(e: Throwable) {
                        isError.value = true
                        isLoading.value = false
                        e.printStackTrace()
                    }

                })
        )

    }

    fun getImageUrlFromAPI(cat: Cat, view: ImageView) {
        cat.imageId?.let {
            disposable.addAll(
                theCatAPIService.getImageDataFromAPI(it)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<Image>() {
                        override fun onSuccess(image: Image) {
                            cat.imageUrl = image.imageUrl
                            view.addImageWithGlide(cat.imageUrl)
                        }

                        override fun onError(e: Throwable) {
                            e.printStackTrace()
                        }
                    })
            )
        }
    }

    fun insertFavoriteToDB(cat: Cat?) {
        launch {
            try {
                cat?.let { dao.insertFavoriteCatBreed(it) }
            } catch (e: Exception) {
                Toast.makeText(
                    getApplication(),
                    "Already added to your favorite list",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }

    fun searchCatBreedInDB(apiId: String?, tgView: ToggleButton) {
        launch {
            val cat = apiId?.let { dao.getCatBreed(it) }
            if (cat != null) {
                tgView.isChecked = cat.isFavorite
            }
        }
    }


}