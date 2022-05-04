package com.example.catbreeds.view

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catbreeds.R
import com.example.catbreeds.adapter.BreedListAdapter
import com.example.catbreeds.viewmodel.CatBreedListViewModel
import kotlinx.android.synthetic.main.fragment_cat_breed_list.*


class CatBreedListFragment : Fragment() {

    private lateinit var viewModel: CatBreedListViewModel
    private lateinit var breedListAdapter: BreedListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cat_breed_list, container, false)
        val toolbar = view.findViewById<Toolbar>(R.id.toolbarCatBreeds)
        toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.btnFavorite) {
                val action =
                    CatBreedListFragmentDirections.actionCatBreedListFragmentToFavoriteCatBreedListFragment()
                Navigation.findNavController(view).navigate(action)
            }
            true
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(CatBreedListViewModel::class.java)
        viewModel.getDataFromAPI()

        createNewAdapter()

        swipeRefreshLayout.setOnRefreshListener {
            viewModel.getDataFromAPI()
            swipeRefreshLayout.isRefreshing = false
        }

        ivSearchBtn.setOnClickListener {
            val catBreed = etCatBreed.text.toString()
            if (catBreed == "") {
                viewModel.getDataFromAPI()
            } else {
                viewModel.searchDataInAPI(etCatBreed.text.toString())
                etCatBreed.setText("")
            }
            closeKeyboard()
        }

        observeLiveData()
    }

    private fun closeKeyboard() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

    private fun createNewAdapter() {
        breedListAdapter = BreedListAdapter()
        recyclerViewBreedList.layoutManager = LinearLayoutManager(context)
        recyclerViewBreedList.adapter = breedListAdapter
    }

    private fun observeLiveData() {
        viewModel.breedList.observe(viewLifecycleOwner, Observer {
            it?.let {
                recyclerViewBreedList.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
                breedListAdapter.updateBreedList(it)
            }
        })


        viewModel.isError.observe(viewLifecycleOwner, Observer {
            if (it) {
                tvLoadingError.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
                recyclerViewBreedList.visibility = View.GONE
            } else {
                tvLoadingError.visibility = View.GONE
            }
        })

        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            if (it) {
                progressBar.visibility = View.VISIBLE
                tvLoadingError.visibility = View.GONE
                recyclerViewBreedList.visibility = View.GONE
            } else {
                progressBar.visibility = View.GONE
            }
        })
    }


}