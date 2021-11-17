package com.example.e_commerce.view.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce.model.Product.Product
import com.example.e_commerce.reposirotries.ApiServiceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


private const val TAG = "ProductsViewModel"

class ProductsViewModel :ViewModel () {

  private val apiRepo = ApiServiceRepository.get()

   val productsLiveData = MutableLiveData<List<Product>>()

    fun callProducts(){

    viewModelScope.launch(Dispatchers.IO) {

        try {
          val response = apiRepo.getProducts()
           if (response.isSuccessful) {
               response.body()?.run {
             Log.d(TAG, this.toString())
             productsLiveData.postValue(products)

               }

           } else{
               Log.d(TAG, response.message())

           }

        } catch (e: Exception)
        {

        }
    }
    }

}