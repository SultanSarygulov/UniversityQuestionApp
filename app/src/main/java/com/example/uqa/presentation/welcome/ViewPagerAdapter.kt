package com.example.uqa.presentation.welcome

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.uqa.R

class ViewPagerAdapter: RecyclerView.Adapter<ViewPagerAdapter.ViewPagerHolder>(){

    val imageList = listOf(
        R.drawable.onboarding1,
        R.drawable.onboarding2,
        R.drawable.onboarding3
    )

    val textList = listOf(
        "Ask questions and get answers",
        "Help others answering their questions",
        "Join or create your own communities"
    )

    class ViewPagerHolder(item: View): RecyclerView.ViewHolder(item){
        val welcomeImage = item.findViewById<ImageView>(R.id.welcome_image)
        val welcomeText = item.findViewById<TextView>(R.id.welcome_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerHolder {
        val view = LayoutInflater.from((parent.context)).inflate(R.layout.item_welcome, parent, false)
        return ViewPagerHolder(view)
    }

    override fun onBindViewHolder(holder: ViewPagerHolder, position: Int) {
        holder.welcomeImage.setImageResource(imageList[position])
        holder.welcomeText.text = textList[position]
    }

    override fun getItemCount(): Int {
        return imageList.size
    }
}