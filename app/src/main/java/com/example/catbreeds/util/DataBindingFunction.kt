package com.example.catbreeds.util

import android.app.Application
import android.widget.ImageView
import android.widget.ToggleButton
import androidx.databinding.BindingAdapter
import com.example.catbreeds.model.Cat
import com.example.catbreeds.viewmodel.CatBreedListViewModel
import com.example.catbreeds.viewmodel.FavoriteListViewModel
import kotlinx.android.synthetic.main.cat_breed_row_item.view.*

private val viewModelList = CatBreedListViewModel(Application())
private val viewModelFavorites = FavoriteListViewModel(Application())

@BindingAdapter("android:getImageUrl")
fun getImageUrl(view: ImageView, cat: Cat) {
    viewModelList.getImageUrlFromAPI(cat, view)
}

@BindingAdapter("android:addImage")
fun addImage(view: ImageView, cat: Cat?) {
    view.addImageWithGlide(cat?.imageUrl)
}


@BindingAdapter("android:searchBreedInDB")
fun searchBreedInDB(view: ToggleButton, cat: Cat) {
    viewModelList.searchCatBreedInDB(cat.apiID, view)
    view.isChecked = cat.isFavorite

    view.setOnClickListener {
        cat.isFavorite = it.tgFavoriteRowBreed.isChecked
        if (cat.isFavorite) {
            viewModelList.insertFavoriteToDB(cat)
        } else {
            viewModelFavorites.deleteFavoriteFromDB(cat.apiID)
        }
    }
}