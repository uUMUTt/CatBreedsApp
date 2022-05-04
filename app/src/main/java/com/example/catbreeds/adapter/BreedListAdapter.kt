package com.example.catbreeds.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.catbreeds.R
import com.example.catbreeds.databinding.CatBreedRowItemBinding
import com.example.catbreeds.model.Cat
import com.example.catbreeds.util.IClickItem

import com.example.catbreeds.view.CatBreedListFragmentDirections
import kotlinx.android.synthetic.main.cat_breed_row_item.view.*

class BreedListAdapter :
    RecyclerView.Adapter<BreedListAdapter.BreedViewHolder>(), IClickItem {

    private val breedList: ArrayList<Cat> = arrayListOf()

    class BreedViewHolder(var view: CatBreedRowItemBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<CatBreedRowItemBinding>(
            inflater,
            R.layout.cat_breed_row_item,
            parent,
            false
        )
        return BreedViewHolder(view)
    }

    override fun onBindViewHolder(holder: BreedViewHolder, position: Int) {

        holder.view.cat = breedList[position]
        holder.view.iClickItem = this
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
        cat.isFavorite = view.tgFavoriteRowBreed.isChecked
        val action =
            CatBreedListFragmentDirections.actionCatBreedListFragmentToBreedDetailFragment(
                cat
            )
        Navigation.findNavController(view).navigate(action)
    }


}