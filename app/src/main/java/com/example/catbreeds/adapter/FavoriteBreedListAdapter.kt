package com.example.catbreeds.adapter

import android.app.Application
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.catbreeds.R
import com.example.catbreeds.databinding.FavoriteBreedRowItemBinding
import com.example.catbreeds.model.Cat
import com.example.catbreeds.util.IClickItem
import com.example.catbreeds.view.FavoriteCatBreedListFragmentDirections
import com.example.catbreeds.viewmodel.FavoriteListViewModel

class FavoriteBreedListAdapter(application: Application) :
    RecyclerView.Adapter<FavoriteBreedListAdapter.FavoriteBreedViewHolder>(), IClickItem {

    private val viewModelFavorites = FavoriteListViewModel(application)

    private var breedList: ArrayList<Cat> = arrayListOf()

    class FavoriteBreedViewHolder(var view: FavoriteBreedRowItemBinding) :
        RecyclerView.ViewHolder(view.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteBreedViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<FavoriteBreedRowItemBinding>(
            inflater,
            R.layout.favorite_breed_row_item,
            parent,
            false
        )
        return FavoriteBreedViewHolder(view)
    }


    override fun onBindViewHolder(holder: FavoriteBreedViewHolder, position: Int) {
        holder.view.cat = breedList[position]
        holder.view.iClickItem = this
        holder.view.tgListener = this
    }


    fun deleteItem(cat: Cat) {
        viewModelFavorites.deleteFavoriteFromDB(cat.apiID)
        breedList.removeAt(breedList.indexOf(cat))
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        return breedList.size
    }

    fun updateBreedList(newBreedList: List<Cat>) {
        breedList.clear()
        breedList.addAll(newBreedList)
        notifyDataSetChanged()
    }

    override fun goDetail(view: View, cat: Cat) {
        val action =
            FavoriteCatBreedListFragmentDirections.actionFavoriteCatBreedListFragmentToCatBreedDetailFragment(
                cat
            )
        Navigation.findNavController(view).navigate(action)
    }

}