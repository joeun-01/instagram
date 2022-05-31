package com.example.instagram.data

data class Post(
    // user 정보는 userDao로 가져오는 걸로 고려 중
    var picture : Int,  // 게시물 사진, 일단 하나로 설정ㅎㅅㅎ
    var context : String  // 게시물 내용
)
