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

    @Query("SELECT password FROM UserTable WHERE ID = :ID")
    fun login(ID : String) : String?
}