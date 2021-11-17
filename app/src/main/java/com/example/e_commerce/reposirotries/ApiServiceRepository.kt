package com.example.e_commerce.reposirotries

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.example.e_commerce.api.CommerceApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

 const val SHARED_PREF_FILE = "Auth"
const val TOKEN_KEY = "token"
private const val BASE_URL = "http://18.196.156.64"

class ApiServiceRepository (val conext: Context) {
// BUILDER FOR RETROFIT

 private val retrofitService = Retrofit.Builder()
     .baseUrl(BASE_URL)
     .addConverterFactory(GsonConverterFactory.create())
     .build()

 // API FOR RETROFIT
    private val retrofitApi = retrofitService.create(CommerceApi::class.java)
   //
    private val sharedPref = conext.getSharedPreferences(SHARED_PREF_FILE,Context.MODE_PRIVATE)

    // to use the token
    private val accessToken = sharedPref.getString(TOKEN_KEY, "")
    suspend fun getProducts() =
    retrofitApi.getProducts("Bearer $accessToken")


    // Adding the products to the favorites
    suspend fun addFavoriteProduct(productId: Int) =
    retrofitApi.addFavoriteProduct("Bearer $accessToken" , productId)
    // Removing the products to the favorites
    suspend fun removeFavoriteProduct(productId: Int) =
    retrofitApi.removeFavoriteProduct("Bearer $accessToken" , productId)

   // to make one instance and make it global
    companion object{
    private var instance: ApiServiceRepository? = null
   // design pattern
    fun init (conext: Context){

        if (instance == null)
            instance = ApiServiceRepository(conext)

    }
        fun get(): ApiServiceRepository {

            return instance ?: throw Exception("ApiServiceRepository must be initialized")
        }
    }
}