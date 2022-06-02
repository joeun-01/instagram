package com.example.instagram.room

import androidx.room.*
import com.example.instagram.data.User

@Dao
interface UserDao {
    @Insert
    fun insert(user: User)

    @Delete
    fun delete(user: User)

    @Update
    fun update(user: User)

    @Query("SELECT * FROM UserTable")
    fun getUsers() : List<User>

    @Query("SELECT * FROM UserTable WHERE userIdx = :userIdx")
    fun getUser(userIdx : Int) : User

    @Query("SELECT userIdx FROM UserTable WHERE ID = :ID")
    fun getUserIdx(ID : String) : Int

    @Query("SELECT password FROM UserTable WHERE ID = :ID")
    fun login(ID : String) : String?
}