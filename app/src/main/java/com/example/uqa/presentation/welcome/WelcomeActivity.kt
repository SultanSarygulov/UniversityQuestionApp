package com.example.uqa.presentation.welcome

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.uqa.databinding.ActivityWelcomeBinding
import com.example.uqa.presentation.MainActivity

class WelcomeActivity : AppCompatActivity() {

//    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.enterButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            this.finish()
        }

        setupViewPager()

    }

    private fun setupViewPager() {
        val viewPager = binding.welcomeViewPager
        viewPager.adapter = ViewPagerAdapter()

        binding.dotsIndicator.attachTo(viewPager)
    }
}