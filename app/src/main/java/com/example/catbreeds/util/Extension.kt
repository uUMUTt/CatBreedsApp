package com.example.catbreeds.util

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.addImageWithGlide(imageURL: String?) {
    imageURL?.let {
        Glide.with(context).load(it).into(this)
    }
}

