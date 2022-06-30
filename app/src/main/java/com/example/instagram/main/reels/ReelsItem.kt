package com.example.instagram.main.reels

data class ReelsItem(
    var userId: String? = "", //사용자 아이디
    var userImg: Int?=null,   //사용자 사진
    var content: String? = "",  //릴스 내용
    var ReelsImg: Int? = null)  //릴스 동영상