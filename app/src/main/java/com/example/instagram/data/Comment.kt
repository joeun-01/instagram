package com.example.instagram.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "CommentTable")
data class Comment (  // 댓글 data class
    var userIdx : Int,  // 누가 단 댓글인지
    var postIdx : Int,  // 어느 게시물에 단 댓글인지
    var comment : String,  // 댓글 내용
    var date : String  // 언제 단 댓글인지
)
{
    @PrimaryKey(autoGenerate = true) var commentIdx : Int = 0
}
