package com.example.instagram.data

data class CountDB (
    // 이런저런 편의를 위한.. 데이터 클래스
    var user : Int = 0,  // 유저 수
    var story : Int = 0,  // 스토리 수
    var post : Int = 0,  // 게시물 수
    var comment : Int = 0,  // 댓글 수
    var reply : Int = 0  // 답글 수
)
