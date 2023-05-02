package com.example.uqa.presentation.askQuestion

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.uqa.data.Post
import com.example.uqa.data.room.PostsRepository
import com.example.uqa.data.room.UQADatabase
import kotlinx.coroutines.launch

class AskQuestionViewModel(application: Application): AndroidViewModel(application) {

    private val repository: PostsRepository

    fun addPost(post: Post){
        viewModelScope.launch {
            repository.addPosts(post)
        }
    }

    init {
        val postsDao = UQADatabase.getDatabase(application).postsDao()
        repository = PostsRepository(postsDao)
    }
}