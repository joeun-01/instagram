package com.example.instagram.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PostPictureTable")
data class PostPicture(
    // 게시물에 사진이 여러 장일 경우 postIdx를 이용하여 불러오기 위한 data class, 나중에 아마 사용하겠죠..?! 일단은 한 장으로
    var picture : Int,  // 게시물 사진
    var postIdx : Int  // 어느 게시물에 있는 사진인지
){
    @PrimaryKey(autoGenerate = true) var pictureIdx : Int = 0
}
