package com.example.instagram.data

data class ReplyDB (
    // 답글 데이터 저장
    var uid : String = "",  // 누가 단 답글인지
    var postIdx : Int = 0,  // 어느 게시물에 단 답글인지
    var commentIdx : Int = 0,  // 어느 댓글에 단 답글인지
    var comment : String = "",  // 답글 내용
    var date : String = "",  // 언제 단 답글인지
    var like : Int = 0,  // 좋아요가 몇 개 달렸는지
)
