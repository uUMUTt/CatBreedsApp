package com.example.catbreeds.view

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catbreeds.R
import com.example.catbreeds.adapter.FavoriteBreedListAdapter
import com.example.catbreeds.viewmodel.FavoriteListViewModel
import kotlinx.android.synthetic.main.fragment_favorite_cat_breed_list.*

class FavoriteCatBreedListFragment : Fragment() {

    private lateinit var viewModel: FavoriteListViewModel
    private lateinit var favoriteBreedListAdapter: FavoriteBreedListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorite_cat_breed_list, container, false)
        val toolbar = view.findViewById<Toolbar>(R.id.toolbarFavoriteCatBreeds)
        toolbar.setNavigationOnClickListener {
            val action =
                FavoriteCatBreedListFragmentDirections.actionFavoriteCatBreedListFragmentToCatBreedListFragment()
            Navigation.findNavController(view).navigate(action)
        }
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(FavoriteListViewModel::class.java)
        viewModel.getDataFromDB()

        createNewAdapter()

        observeLiveData()


    }

    private fun createNewAdapter() {
        favoriteBreedListAdapter = FavoriteBreedListAdapter(requireActivity().application)
        recyclerViewFavoriteBreedList.layoutManager = LinearLayoutManager(context)
        recyclerViewFavoriteBreedList.adapter = favoriteBreedListAdapter
    }

    private fun observeLiveData() {
        viewModel.favoriteList.observe(viewLifecycleOwner, Observer {
            favoriteBreedListAdapter.updateBreedList(it)
            viewModel.isBlankFavoriteList()
        })

        viewModel.isBlankList.observe(viewLifecycleOwner, Observer {
            if (it) {
                tvBlankFavoriteList.visibility = View.VISIBLE
            } else {
                tvBlankFavoriteList.visibility = View.GONE
            }
        })
    }


}