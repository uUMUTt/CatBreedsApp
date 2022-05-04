package com.example.catbreeds.util

import android.view.View
import com.example.catbreeds.model.Cat

interface IClickItem {

    fun goDetail(view: View, cat: Cat)
}