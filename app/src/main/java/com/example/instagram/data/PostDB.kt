package com.example.instagram.data

data class PostDB (
    // 게시물 정보 저장
    var uid : String? = "",  // 게시물을 올린 사람
    var postIdx : Int = 0,  // 게시물 구분
    var picture : Int = 0,  // 게시물 사진, 일단 한 개
    var text : String = "",  // 게시물 내용
    var date : String= "",  // 게시물 올린 시간
    var like : Int = 0  // 좋아요가 눌린 수
)
