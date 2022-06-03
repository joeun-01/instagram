package com.example.instagram.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PostTable")
data class Post(
    var userIdx: Int,  // 유저 인덱스  <- 이걸로 user 정보를 가져옴
    var picture : Int,  // 게시물 사진, 일단 하나로 설정ㅎㅅㅎ
    var context : String,  // 게시물 내용
    var liked : Boolean, // 좋아요 눌렀는지 여부
    var date : String  // 게시물 올린 시간
){
    @PrimaryKey(autoGenerate = true) var postIdx : Int = 0
}
