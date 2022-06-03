package com.example.instagram.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.instagram.data.Comment
import com.example.instagram.data.Post
import com.example.instagram.data.User

@Database(entities = [User::class, Post::class, Comment::class], version = 1, exportSchema = false)
abstract class InstagramDatabase : RoomDatabase() {
    abstract fun userDao() : UserDao
    abstract fun postDao() : PostDao
    abstract fun CommentDao() : CommentDao

    companion object {
        private var instance : InstagramDatabase? = null

        @Synchronized
        fun getInstance(context: Context) : InstagramDatabase? {
            if(instance == null){
                synchronized(InstagramDatabase::class){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        InstagramDatabase::class.java,
                        "song-database"
                    ).allowMainThreadQueries().build()
                }
            }
            return instance
        }
    }
}