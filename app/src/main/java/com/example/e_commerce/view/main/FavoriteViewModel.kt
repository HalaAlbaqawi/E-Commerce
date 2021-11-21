package com.example.e_commerce.view.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce.model.Product.Product
import com.example.e_commerce.model.identity.UserInfoModel
import com.example.e_commerce.reposirotries.ApiServiceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


private const val TAG = "FavoriteViewModel"
class FavoriteViewModel: ViewModel() {

private val apiRepo = ApiServiceRepository.get()

    val favoritesLiveData = MutableLiveData<List<Product>>()
    val favoritesErrorLiveData = MutableLiveData<String>()

    fun callFavorite(){
     viewModelScope.launch(Dispatchers.IO) {

         try {
             val response = apiRepo.getFavoriteProducts()
             if (response.isSuccessful){

                 response.body()?.run {
                 favoritesLiveData.postValue(this)
                 Log.d(TAG,this.toString())
                 }
             } else{
                 Log.d(TAG, response.message())
                 favoritesErrorLiveData.postValue(response.message())
             }
         } catch (e:Exception){
        Log.d(TAG, e.message.toString())
             favoritesErrorLiveData.postValue(e.message.toString())
         }
     }

    }
}