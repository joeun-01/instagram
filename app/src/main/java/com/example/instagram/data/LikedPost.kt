package com.example.instagram.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "LikedPostTable")
data class LikedPost(
    // 게시물 좋아요를 위한 데이터 클래스
    var userIdx : Int,  // 좋아요를 누른 유저 아이디
    var postIdx : Int  // 좋아요 누른 게시물 아이디
)
{
    @PrimaryKey(autoGenerate = true) var id : Int = 0
}