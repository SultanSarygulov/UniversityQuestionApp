package com.example.uqa.presentation

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        // Set NavController
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        navController =navHostFragment.navController

        setupActionBarWithNavController(navController)
        binding.bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener{_, nd: NavDestination, _ ->
            if (nd.id == R.id.questionFragment){
                binding.bottomNavigationView.menu.findItem(R.id.comment).isVisible
                !binding.bottomNavigationView.menu.findItem(R.id.askQuestionFragment).isVisible
            } else {
                !binding.bottomNavigationView.menu.findItem(R.id.comment).isVisible
                binding.bottomNavigationView.menu.findItem(R.id.askQuestionFragment).isVisible
            }
        }

        sharedPreferences = getSharedPreferences("UQA",Context.MODE_PRIVATE)
        if (sharedPreferences.getBoolean(PREFERENCE_KEY, true)) {
            val editor = sharedPreferences.edit()
            editor.putBoolean(PREFERENCE_KEY, true)
            editor.apply()
        }

        setPreferences()


    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.fragmentContainer)
        return navController.navigateUp()
    }



    private fun setPreferences() {
        val preferences = getSharedPreferences("UQA", Activity.MODE_PRIVATE)
        val firstTime = preferences?.getBoolean(PREFERENCE_KEY, true)
        Log.d(TAG, "preferences: ${firstTime}")
        if (firstTime == true){
            loadDemoVideos()

            Log.d(TAG, "preferences: I love children")
            val editor = preferences.edit()
            editor?.putBoolean(PREFERENCE_KEY, false)
            editor?.apply()


        } else {
            Log.d(TAG, "preferences: Nah jk")
        }
    }

    private fun loadDemoVideos() {
        val post1 = Post(0, "Where is mr Isaev", "Sultan Sarygulov (Student)", "12/05/2023", 21, 3, false, false)
        viewModel.addPost(post1)
        val post2 = Post(1, "Have you seen my id?", "daniil (Student)", "12/05/2023", 12, 1, false, false)
        viewModel.addPost(post2)
        val post3 = Post(2, "Where is the Finals schedule", "Andrei (Teacher)", "12/05/2023", 30, 0, false, false)
        viewModel.addPost(post3)
        val post4 = Post(3, "Recommend a nice restaurant near the university", "Muntaha", "12/05/2023", 0, 99, false, false)
        viewModel.addPost(post4)

    }

    companion object{
        val TAG = "Chura"
        val PREFERENCE_KEY = "First time9"
    }
}