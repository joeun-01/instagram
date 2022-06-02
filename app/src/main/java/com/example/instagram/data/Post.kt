package com.example.instagram.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PostTable")
data class Post(
    // user 정보는 userDao로 가져오는 걸로 고려 중
    var userID: String,  // 유저 아이디
    var picture : Int,  // 게시물 사진, 일단 하나로 설정ㅎㅅㅎ
    var context : String,  // 게시물 내용
    var liked : Boolean, // 좋아요 눌렀는지 여부
){
    @PrimaryKey(autoGenerate = true) var postIdx : Int = 0
}
