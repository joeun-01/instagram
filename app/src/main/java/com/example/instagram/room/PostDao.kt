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

    @Query("SELECT * FROM PostTable WHERE postIdx = :postIdx")
    fun getPost(postIdx : Int) : Post

    // 게시물 정보 받아오기

    // 좋아요 관련 DAO
    @Query("UPDATE PostTable SET liked = :liked WHERE userIdx = :userIdx")
    fun updateLikeByID(liked: Boolean, userIdx: Int)

    @Query("SELECT liked FROM PostTable WHERE userIdx = :userIdx")
    fun getLikeByID(userIdx: Int) : Boolean

//
//    @Query("SELECT AT.* FROM UserTable WHERE ")
//
//    @Query("SELECT AT.* FROM UserTable as LT LEFT JOIN Comm as AT on LT.albumId = AT.id WHERE LT.jwt = :jwt")
//    fun getLikedAlbums(jwt: String?) : List<Album>
}