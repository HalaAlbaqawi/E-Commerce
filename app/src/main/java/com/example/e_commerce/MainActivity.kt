package com.example.e_commerce

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.e_commerce.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    // to control the nav
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

     val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as
     NavHostFragment
     navController = navHostFragment.navController
   // we change the fragment names in the main graph then we put this to change the bottom bar name
        // when we move from the screen to screen
     setupActionBarWithNavController(navController)
     // to connect bottom nav with botton host & it run the the bottom
     NavigationUI.setupWithNavController(binding.bottomNavigation, navController)

    }
  // to activate the back button in the app bar and make it work
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

}