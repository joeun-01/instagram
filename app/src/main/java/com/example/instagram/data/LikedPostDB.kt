package com.example.instagram.data

data class LikedPostDB(
    // 게시물 좋아요를 위한 데이터 클래스
    var uid : String = "",  // 좋아요를 누른 유저 아이디
    var postIdx : Int = 0  // 좋아요 누른 게시물 아이디
)