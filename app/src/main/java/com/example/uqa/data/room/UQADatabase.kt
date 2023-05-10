package com.example.uqa.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.uqa.data.Answer
import com.example.uqa.data.Post

@Database(entities = [Post::class, Answer::class], version = 7, exportSchema = false)
abstract class UQADatabase: RoomDatabase() {

    abstract fun postsDao(): PostsDao

    companion object{
        @Volatile
        private var INSTANCE: UQADatabase? = null

        fun getDatabase(context: Context): UQADatabase{
            val tempInstance = INSTANCE

            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UQADatabase::class.java,
                    "uqa_database"
                    ).fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                return instance
            }
        }
    }
}