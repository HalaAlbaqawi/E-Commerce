package com.example.e_commerce.view.identity

import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.e_commerce.R
import com.example.e_commerce.databinding.FragmentLoginBinding
import com.example.e_commerce.databinding.FragmentRegisterBinding
import com.example.e_commerce.util.RegisterValidation

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val registerViewModel: RegisterViewModel by activityViewModels()
// object, this what make test for and we will use it here
    private val validator = RegisterValidation()
    private lateinit var progressDialog: ProgressDialog


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        progressDialog = ProgressDialog(requireActivity())
        progressDialog.setTitle("Loading...")
        progressDialog.setCancelable(false)

        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
      // call the function
        observers()

        binding.registerButton.setOnClickListener {
            val firstName = binding.firstNameEdittext.text.toString()
            val lastName = binding.lastNameEdittext.text.toString()
            val email = binding.emailEdittext.text.toString()
            val passowrd = binding.passwordEdittext.text.toString()
            val confirmpassowrd = binding.confirmPasswordEdittext.text.toString()

            if (firstName.isNotBlank() && lastName.isNotBlank() && email.isNotBlank() &&
                passowrd.isNotBlank() && confirmpassowrd.isNotBlank()
            ) {

                // if condition to make the password and confirm password equal
                if(passowrd == confirmpassowrd) {
                    if (validator.emailIsValid(email)) {
                        if (validator.passwordIsValid(passowrd)) {
                            progressDialog.show()
                            registerViewModel.register(firstName,lastName,email,passowrd)
                        } else
                            Toast.makeText(requireActivity(), "Make sure your password is strong.", Toast.LENGTH_SHORT).show()
                    } else
                        Toast.makeText(requireActivity(), "Make sure you typed your email address correctly.", Toast.LENGTH_SHORT).show()
                } else
                    Toast.makeText(requireActivity(), "Password and confirm password doesn't match", Toast.LENGTH_SHORT).show()
            }else
                Toast.makeText(requireActivity(), "Registration fields must not be empty", Toast.LENGTH_SHORT).show()

            }
        }

    fun observers(){
        // successful response
        registerViewModel.registerLiveData.observe(viewLifecycleOwner,{
         it?.let {
             progressDialog.dismiss()
             findNavController().popBackStack()
         }

        })
         // error response
            registerViewModel.registerErrorLiveData.observe(viewLifecycleOwner ,{
                progressDialog.dismiss()
                Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()


            })

    }
    }