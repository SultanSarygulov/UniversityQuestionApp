package com.example.uqa.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "answers_table")
data class Answer(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val postId: Long,
    val replyId: Long? = null,
    val text: String,
    val author: String
)
