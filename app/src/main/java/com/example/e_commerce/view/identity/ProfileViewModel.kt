package com.example.e_commerce.view.identity

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce.model.identity.LoginBody
import com.example.e_commerce.model.identity.UserInfoModel
import com.example.e_commerce.reposirotries.ApiServiceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File


private const val TAG = "ProfileViewModel"

class ProfileViewModel: ViewModel() {

    private val apiRepo = ApiServiceRepository.get()

    // live data response
    val profileLiveData = MutableLiveData<UserInfoModel>()
    val uploadImageLiveData = MutableLiveData<String>()

    // error live data response
    val profileErrorLiveData = MutableLiveData<String>()

// calling user profile and send the data to live data to observe the data profile
    fun callUserProfile() {
        viewModelScope.launch(Dispatchers.IO) {

            try {
                val response = apiRepo.getUserInfo()

                if (response.isSuccessful)
                   response.body()?.run {
                   profileLiveData.postValue(this)
                   Log.d(TAG,this.toString())
                   } else{
                       Log.d(TAG, response.message())
                    profileErrorLiveData.postValue(response.message().toString())
                   }
            } catch (e: Exception){
                Log.d(TAG, e.message.toString())
                profileErrorLiveData.postValue(e.message.toString())
            }
        }
    }

    fun uploadUserImage(file: File){
      viewModelScope.launch(Dispatchers.IO) {
          try {
              val response = apiRepo.uploadUserImage(file)
              if (response.isSuccessful){
                  uploadImageLiveData.postValue("successful")
              }else{
                  Log.d(TAG, response.message())
                  profileErrorLiveData.postValue(response.message())
              }

          } catch (e: Exception){
              Log.d(TAG, e.message.toString())
              profileErrorLiveData.postValue(e.message.toString())
          }
      }


    }
}