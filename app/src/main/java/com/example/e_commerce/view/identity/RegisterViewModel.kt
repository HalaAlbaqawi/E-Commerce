package com.example.e_commerce.view.identity

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce.model.identity.RegisterBody
import com.example.e_commerce.reposirotries.ApiServiceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "RegisterViewModel"
class RegisterViewModel: ViewModel() {

 private val apiRepo = ApiServiceRepository.get()

 val registerLiveData = MutableLiveData<String>()
 val registerErrorLiveData = MutableLiveData<String>()

 fun register(firstName: String, lastName: String, email: String, password: String){
  viewModelScope.launch(Dispatchers.IO) {
   try {
  val response = apiRepo.register(RegisterBody(email,firstName,lastName,password, 1))

    if (response.isSuccessful){
     Log.d(TAG,response.body().toString())
     registerLiveData.postValue("successful")
    } else{
     Log.d(TAG,response.message())
     registerErrorLiveData.postValue(response.message())
    }


   } catch (e: Exception){
    Log.d(TAG,e.message.toString())
    registerErrorLiveData.postValue(e.message.toString())
   }

  }

 }
}