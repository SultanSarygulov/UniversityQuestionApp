package com.example.uqa.presentation.question

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.uqa.data.Answer
import com.example.uqa.data.Post
import com.example.uqa.data.room.PostsRepository
import com.example.uqa.data.room.UQADatabase
import kotlinx.coroutines.launch

class QuestionViewModel(application: Application): AndroidViewModel(application) {

    private val repository: PostsRepository
    val _answersList = MutableLiveData<List<Answer>>()
    val answersList: LiveData<List<Answer>>
        get() = _answersList

    fun getAnswers(postId: Long){
        viewModelScope.launch {
            _answersList.postValue(repository.getAnswers(postId))
        }
    }

    fun addAnswer(answer: Answer){
        viewModelScope.launch{
            repository.addAnswer(answer)
            getAnswers(answer.postId)
        }
    }

    init {
        val dao = UQADatabase.getDatabase(application).postsDao()
        repository = PostsRepository(dao)

    }


}