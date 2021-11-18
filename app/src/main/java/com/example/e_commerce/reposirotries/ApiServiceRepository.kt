package com.example.e_commerce.reposirotries

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.example.e_commerce.api.CommerceApi
import com.example.e_commerce.model.identity.LoginBody
import com.example.e_commerce.model.identity.RegisterBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.io.File

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

//    // to use the token
//    private val accessToken = sharedPref.getString(TOKEN_KEY, "")
    suspend fun getProducts() =
    retrofitApi.getProducts("Bearer ${sharedPref.getString(TOKEN_KEY, "")}")


    // Adding the products to the favorites
    suspend fun addFavoriteProduct(productId: Int) =
    retrofitApi.addFavoriteProduct("Bearer ${sharedPref.getString(TOKEN_KEY, "")}" , productId)
    // Removing the products to the favorites
    suspend fun removeFavoriteProduct(productId: Int) =
    retrofitApi.removeFavoriteProduct("Bearer ${sharedPref.getString(TOKEN_KEY, "")}" , productId)
  // to register
    suspend fun register(registerBody: RegisterBody) =
        retrofitApi.userRegister(registerBody)
   // to login
    suspend fun login (loginBody: LoginBody) =
        retrofitApi.userLogin(loginBody)
   // profile fun
    suspend fun getUserInfo() =
        retrofitApi.getUserInfo(("Bearer ${sharedPref.getString(TOKEN_KEY, "")}"))
   // to upload any file , you only need to change the file type inside "()"
    suspend fun uploadUserImage(file: File) : Response<ResponseBody> {
    val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
     //// to convert the file to multi part body
    val body = MultipartBody.Part.createFormData("imageFile",file.name,requestFile)

     return retrofitApi.uploadImage("Bearer ${sharedPref.getString(TOKEN_KEY, "")}", body)
    }


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