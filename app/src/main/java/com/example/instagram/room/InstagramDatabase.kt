package com.example.instagram.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.instagram.data.*

@Database(entities = [User::class, LikedPost::class], version = 1, exportSchema = false)
abstract class InstagramDatabase : RoomDatabase() {
    abstract fun userDao() : UserDao

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