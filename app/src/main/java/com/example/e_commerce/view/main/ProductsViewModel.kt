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
   // live data to call the product
   val productsLiveData = MutableLiveData<List<Product>>()
    //
   val productsErrorLiveData = MutableLiveData<String>()

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

    fun addFavoriteProduct(productId: Int){
       viewModelScope.launch(Dispatchers.IO) {
           try {
           val response = apiRepo.addFavoriteProduct(productId)
           // handel the fail if we put product to favorite
           if (!response.isSuccessful)  {
            Log.d(TAG,response.message())
            productsErrorLiveData.postValue(response.message())

           }
           } catch (e:Exception){
           Log.d(TAG, e.message.toString())

           }



       }


    }

    fun removeFavoriteProduct(productId: Int){



    }

}