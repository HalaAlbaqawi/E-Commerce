package com.example.e_commerce.view.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.e_commerce.R
import com.example.e_commerce.databinding.FragmentFavoriteBinding
import com.example.e_commerce.view.adapter.FavoriteRecyclerViewAdapter
import com.example.e_commerce.view.adapter.ProductsRecyclerViewAdapter

class FavoriteFragment : Fragment() {

private lateinit var binding: FragmentFavoriteBinding
private val favoriteViewModel: FavoriteViewModel by activityViewModels()
private lateinit var favoriteRecyclerAdapter: FavoriteRecyclerViewAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observers()

        favoriteRecyclerAdapter = FavoriteRecyclerViewAdapter()
        binding.favoriteRecyclerView.adapter = favoriteRecyclerAdapter
        favoriteViewModel.callFavorite()

        binding.favoriteLoginButton.setOnClickListener {
         findNavController().navigate(R.id.action_favoriteFragment_to_loginFragment)

        }
    }

      fun observers(){
       favoriteViewModel.favoritesLiveData.observe(viewLifecycleOwner,{
        binding.favoriteProgressBar.animate().alpha(0f)
        favoriteRecyclerAdapter.submitList(it)
       })
       favoriteViewModel.favoritesErrorLiveData.observe(viewLifecycleOwner,{
           // to hide the progress bar after you logout
           binding.favoriteProgressBar.visibility = View.INVISIBLE
        it?.let {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
        if (it == "Unauthorized"){
         // to show the login button
         binding.favoriteLoginButton.visibility = View.VISIBLE
         // to hide the favorites after you logout
         favoriteRecyclerAdapter.submitList(listOf())
        }  }
           favoriteViewModel.favoritesErrorLiveData.postValue(null)
       })

      }

}