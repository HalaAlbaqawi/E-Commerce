package com.example.e_commerce.view.identity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.e_commerce.R
import com.example.e_commerce.databinding.FragmentProfileBinding
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    // calling view model
    private val profileViewModel: ProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observers()

        profileViewModel.callUserProfile()


    }

    fun observers(){
        profileViewModel.profileLiveData.observe(viewLifecycleOwner,{
          binding.profileProgressBar.animate().alpha(0f)
          binding.emailTextview.text = it.email
          binding.fullnameTextview.text = it.fullName

    Picasso.get().load("http://18.196.156.64/Images/${it.image}").into(binding.profileImageView)

        })
        profileViewModel.profileErrorLiveData.observe(viewLifecycleOwner,{
         binding.profileProgressBar.animate().alpha(0f)
            Toast.makeText(requireActivity(),it, Toast.LENGTH_SHORT).show()

        })

    }

}