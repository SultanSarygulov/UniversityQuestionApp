package com.example.uqa.presentation

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.uqa.R
import com.example.uqa.data.Post
import com.example.uqa.databinding.ActivityMainBinding
import com.example.uqa.presentation.home.HomeViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        // Set NavController
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        navController =navHostFragment.navController

        setupActionBarWithNavController(navController)
        binding.bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener{_, destination: NavDestination, _ ->
            val authorizationFragments = setOf(
                R.id.loginFragment,
                R.id.registerFragment,
                R.id.codeFragment
            )

            if (destination.id in authorizationFragments) {
                binding.bottomNavigationView.visibility = View.GONE
                this.supportActionBar?.hide()
            } else {
                binding.bottomNavigationView.visibility = View.VISIBLE
                this.supportActionBar?.show()
            }
        }

        sharedPreferences = getSharedPreferences("UQA",Context.MODE_PRIVATE)
        if (sharedPreferences.getBoolean(PREFERENCE_KEY, true)) {
            val editor = sharedPreferences.edit()
            editor.putBoolean(PREFERENCE_KEY, true)
            editor.apply()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.fragmentContainer)
        return navController.navigateUp()
    }


    companion object{
        val TAG = "Chura"
        val PREFERENCE_KEY = "First time12"
    }
}