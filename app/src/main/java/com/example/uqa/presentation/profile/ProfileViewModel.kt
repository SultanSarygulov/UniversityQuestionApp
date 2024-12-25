package com.example.uqa.presentation.profile

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.uqa.data.Post
import com.example.uqa.data.room.PostsRepository
import com.example.uqa.data.room.UQADatabase
import com.example.uqa.presentation.MainActivity
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application): AndroidViewModel(application) {

    private var _postsList = MutableLiveData<List<Post>>()
    val myPostsList: LiveData<List<Post>>
    get() = _postsList


    fun getPostsListFromAuthor(author: String) {
        viewModelScope.launch {
            val posts = repository.getPostsByAuthor(author)
            _postsList.postValue(posts)
        }
    }

    private val repository: PostsRepository

    init {
        val postsDao = UQADatabase.getDatabase(application).postsDao()
        repository = PostsRepository(postsDao)
    }

}