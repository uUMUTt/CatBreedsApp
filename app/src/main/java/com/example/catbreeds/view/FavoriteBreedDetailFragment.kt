package com.example.catbreeds.view

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ToggleButton
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.catbreeds.R
import com.example.catbreeds.databinding.FragmentFavoriteBreedDetailBinding
import com.example.catbreeds.viewmodel.FavoriteBreedDetailViewModel
import com.example.catbreeds.viewmodel.CatBreedListViewModel
import com.example.catbreeds.viewmodel.FavoriteListViewModel
import kotlinx.android.synthetic.main.fragment_favorite_breed_detail.*


class FavoriteBreedDetailFragment : Fragment() {

    private lateinit var viewModel: FavoriteBreedDetailViewModel
    private val viewModelList = CatBreedListViewModel(Application())
    private val viewModelFavorite = FavoriteListViewModel(Application())
    private lateinit var apiId: String

    private lateinit var dataBinding: FragmentFavoriteBreedDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_favorite_breed_detail,
            container,
            false
        )
        val toolbar = dataBinding.root.findViewById<Toolbar>(R.id.toolbarCatBreedDetail)
        val tgFavoriteDetail = dataBinding.root.findViewById<ToggleButton>(R.id.tgFavoriteDetail)

        toolbar.setNavigationOnClickListener {
            val action =
                FavoriteBreedDetailFragmentDirections.actionCatBreedDetailFragmentToFavoriteCatBreedListFragment()
            Navigation.findNavController(it).navigate(action)
        }

        tgFavoriteDetail.setOnClickListener {
            if (tgFavoriteDetail.isChecked) {
                viewModelList.insertFavoriteToDB(viewModel.catLiveData.value)
            } else {
                viewModelFavorite.deleteFavoriteFromDB(viewModel.catLiveData.value?.apiID)
            }
        }
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(FavoriteBreedDetailViewModel::class.java)
        arguments?.let {
            viewModel.catLiveData.value = FavoriteBreedDetailFragmentArgs.fromBundle(it).cat
        }

        observeLiveData()
        println(dataBinding.cat?.wikipediaLink)
        goWikipedia(viewModel.catLiveData.value?.wikipediaLink)
    }

    private fun observeLiveData() {
        viewModel.catLiveData.observe(viewLifecycleOwner, Observer {
            dataBinding.cat = it
        })
    }

    private fun goWikipedia(link: String?) {
        tvWikipediaDetail.setOnClickListener {
            link?.let {
                val browserIntent =
                    Intent(Intent.ACTION_VIEW, Uri.parse(it))
                startActivity(browserIntent)
            }
        }
    }

}