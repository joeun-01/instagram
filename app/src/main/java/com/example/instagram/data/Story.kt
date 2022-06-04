package com.example.instagram.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "StoryTable")
data class Story(
    var userIdx : Int, // 누가 올린 스토리인지 유저 정보를 가져올 때 사용
    var picture : Int, // 스토리에 무슨 사진을 올렸는지
    var date : String, // 스토리를 언제 올렸는지
){
    @PrimaryKey(autoGenerate = true) var storyIdx : Int = 0
}
