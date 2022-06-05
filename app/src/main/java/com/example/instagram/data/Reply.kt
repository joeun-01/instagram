package com.example.instagram.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ReplyTable")
data class Reply (
    // 답글 데이터 클래스
    var userIdx : Int,  // 누가 단 답글인지
    var postIdx : Int, // 어느 게시물에 단 답글인지
    var commentIdx : Int,  // 어느 댓글에 단 답글인지
    var comment : String,  // 답글 내용
    var date : String,  // 언제 단 답글인지
    var like : Int,  // 좋아요가 몇 개 달렸는지
){
    @PrimaryKey(autoGenerate = true) var replyIdx : Int = 0
}
