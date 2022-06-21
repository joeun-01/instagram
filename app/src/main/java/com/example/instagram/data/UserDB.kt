package com.example.instagram.data

data class UserDB (
    // 유저 정보 저장
    var email : String = "",
    var password : String = "",
    var ID : String = "",
    var name : String = "",
    var picture : Int = 0,
)