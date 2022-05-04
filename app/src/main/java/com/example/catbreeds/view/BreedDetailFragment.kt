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
import com.example.catbreeds.databinding.FragmentBreedDetailBinding
import com.example.catbreeds.viewmodel.BreedDetailViewModel
import com.example.catbreeds.viewmodel.CatBreedListViewModel
import com.example.catbreeds.viewmodel.FavoriteListViewModel
import kotlinx.android.synthetic.main.fragment_breed_detail.*

class BreedDetailFragment : Fragment() {

    private lateinit var viewModel: BreedDetailViewModel
    private val viewModelList = CatBreedListViewModel(Application())
    private val viewModelFavorite = FavoriteListViewModel(Application())

    private lateinit var dataBinding: FragmentBreedDetailBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_breed_detail,
            container,
            false
        )
        val toolbar = dataBinding.root.findViewById<Toolbar>(R.id.toolbarBreedDetail)
        val tgBreedDetail = dataBinding.root.findViewById<ToggleButton>(R.id.tgBreedDetail)


        toolbar.setNavigationOnClickListener {
            val action =
                BreedDetailFragmentDirections.actionBreedDetailFragmentToCatBreedListFragment()
            Navigation.findNavController(it).navigate(action)
        }

        tgBreedDetail.setOnClickListener {
            if (tgBreedDetail.isChecked) {
                viewModel.catLiveData.value?.isFavorite = true
                viewModelList.insertFavoriteToDB(viewModel.catLiveData.value)
            } else {
                viewModel.catLiveData.value?.isFavorite = false
                viewModelFavorite.deleteFavoriteFromDB(viewModel.catLiveData.value?.apiID)
            }
        }
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(BreedDetailViewModel::class.java)
        arguments?.let {
            viewModel.catLiveData.value = BreedDetailFragmentArgs.fromBundle(it).cat
        }

        observeLiveData()
        goWikipedia(viewModel.catLiveData.value?.wikipediaLink)

    }

    private fun observeLiveData() {
        viewModel.catLiveData.observe(viewLifecycleOwner, Observer {
            dataBinding.cat = it
        })
    }

    private fun goWikipedia(link: String?) {
        tvWikipediaBreedDetail.setOnClickListener {
            link?.let {
                val browserIntent =
                    Intent(Intent.ACTION_VIEW, Uri.parse(it))
                startActivity(browserIntent)
            }
        }
    }

}