package com.example.e_commerce.view.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.e_commerce.R
import com.example.e_commerce.databinding.FragmentProductsBinding
import com.example.e_commerce.view.adapter.ProductsRecyclerViewAdapter


class ProductsFragment : Fragment() {

 private lateinit var binding: FragmentProductsBinding
 // we make the adpter global to use it in different places in the class
 private lateinit var productAdapter: ProductsRecyclerViewAdapter

 private val productsViewModel: ProductsViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // to display the menu
        setHasOptionsMenu(true)

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

       productAdapter = ProductsRecyclerViewAdapter()
       binding.productsRecyclerView.adapter = productAdapter

        // we call the fun
        observer()
       // we call the api here because we want the product shows when we open the app
       productsViewModel.callProducts()

    }

    // observe all the live data
    fun observer(){
     productsViewModel.productsLiveData.observe(viewLifecycleOwner, {

         binding.productsProgressBar.animate().alpha(0f)
         // we put all the data inside the adapter
         productAdapter.submitList(it)
     })

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        // to connect the action bar with menu
        requireActivity().menuInflater.inflate(R.menu.main_menu,menu)
    }
}
