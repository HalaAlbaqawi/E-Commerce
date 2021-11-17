package com.example.e_commerce.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.motion.widget.MotionLayout
import com.example.e_commerce.MainActivity
import com.example.e_commerce.R
import com.example.e_commerce.databinding.ActivityMainBinding
import com.example.e_commerce.databinding.ActivitySplashBinding
import com.example.e_commerce.reposirotries.ApiServiceRepository

class Splash : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySplashBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        // instance from repo
        ApiServiceRepository.init(this)
        setContentView(binding.root)

      binding.motionLayout.setTransitionListener(object  : MotionLayout.TransitionListener{
          override fun onTransitionStarted(motionLayout: MotionLayout?, startId: Int, endId: Int) {

          }

          override fun onTransitionChange(
              motionLayout: MotionLayout?,
              startId: Int,
              endId: Int,
              progress: Float
          ) {

          }

          override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
            // to open the main activity after the splash activity instead of timer
           // splash with motion layout
            var intent = Intent (this@Splash, MainActivity::class.java)
            startActivity(intent)
            finish()

          }

          override fun onTransitionTrigger(
              motionLayout: MotionLayout?,
              triggerId: Int,
              positive: Boolean,
              progress: Float
          ) {

          }


      })


    }
}