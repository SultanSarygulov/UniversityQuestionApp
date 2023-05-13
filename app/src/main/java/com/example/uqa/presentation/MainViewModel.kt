package com.example.uqa.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uqa.data.Post
import com.example.uqa.data.room.PostsRepository
import com.example.uqa.data.room.UQADatabase
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {

    fun addPost(post: Post){
        viewModelScope.launch {
            repository.addPosts(post)
            Log.d(MainActivity.TAG, "addPost: $post")
        }
    }

    private val repository: PostsRepository

    init {
        val postsDao = UQADatabase.getDatabase(application).postsDao()
        repository = PostsRepository(postsDao)
    }


}