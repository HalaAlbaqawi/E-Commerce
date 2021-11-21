package com.example.e_commerce.view.main

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.e_commerce.R
import com.example.e_commerce.databinding.FragmentProductsBinding
import com.example.e_commerce.model.Product.Product
import com.example.e_commerce.reposirotries.SHARED_PREF_FILE
import com.example.e_commerce.reposirotries.TOKEN_KEY
import com.example.e_commerce.view.adapter.ProductsRecyclerViewAdapter


class ProductsFragment : Fragment() {

 private lateinit var binding: FragmentProductsBinding
 // we creat a list to save the data
 private var allProducts = listOf<Product>()
 // we make the adpter global to use it in different places in the class
 private lateinit var productAdapter: ProductsRecyclerViewAdapter
 // Adding the view model to this fragment
 private val productsViewModel: ProductsViewModel by activityViewModels()

 private lateinit var sharedPref: SharedPreferences
 private lateinit var sharedPrefEditor: SharedPreferences.Editor

 private lateinit var logoutItem: MenuItem
 private lateinit var profileItem: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // to display the menu
        setHasOptionsMenu(true)
        // initialized the shared pref
   sharedPref = requireActivity().getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE)
   sharedPrefEditor = sharedPref.edit()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       productAdapter = ProductsRecyclerViewAdapter(productsViewModel)
       binding.productsRecyclerView.adapter = productAdapter

        // we call the fun
        observer()
       // we call the api here because we want the product shows when we open the app
       productsViewModel.callProducts()

    }

    // observe all the live data
    fun observer(){
     productsViewModel.productsLiveData.observe(viewLifecycleOwner, {

         binding.productsProgressBar.animate().alpha(0f).setDuration(1000)
         // we put all the data inside the adapter
         productAdapter.submitList(it)
         allProducts = it
         // to show the recycler view after the new data is coming
         binding.productsRecyclerView.animate().alpha(1f)
     })
     // observe all the error in live data
        productsViewModel.productsErrorLiveData.observe(viewLifecycleOwner,{ error ->
            error?.let {
                // to handle the error
      Toast.makeText(requireActivity(),error,Toast.LENGTH_SHORT).show()
                if (error == "Unauthorized")
        // if you press favorite for any product when you're not login it will take to login fragment
         findNavController().navigate(R.id.action_productsFragment2_to_loginFragment)
         productsViewModel.productsErrorLiveData.postValue(null)
            }


        })
    }
  /// items inside the menu , listen on your click in menu option
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
      when(item.itemId){
      // if you find this button do what written inside the "{}" (under this comment)
        R.id.logout_item -> {

        sharedPrefEditor.putString(TOKEN_KEY,"")
        sharedPrefEditor.commit()
        logoutItem.isVisible = false
        profileItem.isVisible = false

         // to show the progress bar
         binding.productsProgressBar.animate().alpha(1f)
         // to hide the recycler view after logout
         binding.productsRecyclerView.animate().alpha(0f)
        // clear the favorite after you logout
        productsViewModel.callProducts()
        }

        R.id.profile_item -> {
         // to nav us from products to favorite fragment
       findNavController().navigate(R.id.action_productsFragment2_to_profileFragment)
        }

      }
        return super.onOptionsItemSelected(item)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        // to connect the action bar with menu
        requireActivity().menuInflater.inflate(R.menu.main_menu,menu)

        val searchItem = menu.findItem(R.id.search_item)
        logoutItem = menu.findItem(R.id.logout_item)
        profileItem = menu.findItem(R.id.profile_item)
        // we will use this var to search
        val searchView = searchItem.actionView as SearchView
        val token = sharedPref.getString (TOKEN_KEY, "")

        if (token!!.isEmpty()){
         logoutItem.isVisible = false
         profileItem.isVisible = false

        }
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            // when you press the search bar
            override fun onQueryTextSubmit(query: String?): Boolean {

                // we will edit the data in adapter
                productAdapter.submitList(
                    // filttering the data and adding it to the list
                    allProducts.filter {
                        it.description.lowercase().contains(query!!.lowercase())
                    }
                )
                return true
            }
            // when you type inside the search bar
            override fun onQueryTextChange(newText: String?): Boolean {

                return true
            }


        })
        // to expand the search and collapse the text and go back to the products fragment
        searchItem.setOnActionExpandListener(object: MenuItem.OnActionExpandListener{
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {

                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
        // when you finish searching it will put you back to the product fragment
                productAdapter.submitList(allProducts)
                return true
            }


        })
    }
}
