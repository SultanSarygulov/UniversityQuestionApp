package com.example.uqa.presentation.question

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.uqa.data.Answer
import com.example.uqa.data.room.PostsRepository
import com.example.uqa.data.room.UQADatabase

class QuestionViewModel(application: Application): AndroidViewModel(application) {

    private val repository: PostsRepository

    init {
        val dao = UQADatabase.getDatabase(application).postsDao()
        repository = PostsRepository(dao)

    }

    val answersList = MutableLiveData(
        mutableListOf(
            Answer(0, "No idea man", "Mongul Bek"),
            Answer(1, "DM me", "EShlere"),
            Answer(2, "Fuck you", "Bob"),
            Answer(3, "He is in his office", "Sultan V"),
            Answer(4, "I'm Gay", "John Yag")
        )
    )
}