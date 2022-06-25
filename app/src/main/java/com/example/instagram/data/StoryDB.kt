package com.example.instagram.data

data class StoryDB(
    // 스토리 정보 저장
    var uid : String? = "",  // 스토리를 올린 사람
    var picture : Int = 0,   // 스토리에 올린 사진
    var date : String = ""  // 스토리를 올린 시간
)
