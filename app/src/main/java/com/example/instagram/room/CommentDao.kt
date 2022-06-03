package com.example.instagram.room

import androidx.room.*
import com.example.instagram.data.Comment

@Dao
interface CommentDao {

    @Insert
    fun insert(comment: Comment)

    @Delete
    fun delete(comment: Comment)

    @Update
    fun update(comment: Comment)

    @Query("SELECT * FROM CommentTable")
    fun getComments() : List<Comment>
}