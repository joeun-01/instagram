package com.example.instagram.room

import androidx.room.*
import com.example.instagram.data.LikedPost
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
    @Insert
    fun addLikedPost(likedPost: LikedPost)  // 좋아요

    @Query("DELETE FROM LikedPostTable WHERE userIdx = :userIdx AND postIdx = :postIdx")
    fun deleteLikedPost(userIdx: Int, postIdx: Int)  // 좋아요 취소

   @Query("SELECT * FROM LikedPostTable WHERE userIdx = :userIdx AND postIdx = :postIdx")
    fun checkLikedPost(userIdx : Int, postIdx : Int) : List<LikedPost>  // 좋아요한 게시물인지 확인

}