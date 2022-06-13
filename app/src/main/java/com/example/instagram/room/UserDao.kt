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

    // 유저 정보 가져오기
    @Query("SELECT userIdx FROM UserTable WHERE ID = :ID")
    fun getUserIdx(ID : String) : Int

    @Query("SELECT picture FROM UserTable WHERE userIdx = :userIdx")
    fun getUserPicture(userIdx: Int) : Int

    @Query("SELECT ID FROM UserTable WHERE userIdx = :userIdx")
    fun getUserID(userIdx: Int) : String

    @Query("SELECT name FROM UserTable WHERE userIdx = :userIdx")
    fun getUserName(userIdx: Int) : String

    // 로그인 프로세스
    @Query("SELECT password FROM UserTable WHERE ID = :ID")
    fun login(ID : String) : String?
}