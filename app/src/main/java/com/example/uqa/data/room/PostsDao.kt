package com.example.uqa.data.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.uqa.data.Answer
import com.example.uqa.data.Post

@Dao
interface PostsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPost(post: Post)

    @Query("SELECT * FROM posts_table ORDER BY id ASC")
    suspend fun getPosts(): List<Post>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addAnswer(answer: Answer)

    @Query("SELECT * FROM answers_table WHERE postId = :id ORDER BY id ASC")
    suspend fun getAnswers(id: Long): List<Answer>
}