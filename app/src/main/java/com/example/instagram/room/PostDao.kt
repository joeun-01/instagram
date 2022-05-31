package com.example.instagram.room

import androidx.room.*
import com.example.instagram.data.Post

@Dao
interface PostDao {
    @Insert
    fun insert(post: Post)

    @Delete
    fun delete(post: Post)

    @Update
    fun update(post: Post)

    @Query("SELECT * FROM PostTable")
    fun getPosts() : List<Post>

    // 좋아요 관련 DAO
    @Query("UPDATE PostTable SET liked = :liked WHERE userID = :userID")
    fun updateLikeByID(liked: Boolean, userID: String)

    @Query("SELECT liked FROM PostTable WHERE userID = :userID")
    fun getLikeByID(userID: String) : Boolean
}