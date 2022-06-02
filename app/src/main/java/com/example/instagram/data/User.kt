package com.example.instagram.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserTable")
data class User(
    var ID : String,  // 인스타 아이디, 아이디로 로그인 예정
    var password : String,  // 비밀번호
    var picture : Int,  // 프로필 사진
    var name : String,  // 설정할 수도 있고 안할 수도 있는 이름
    // 팔로워랑 팔로잉을 어떻게 할지가 굉장히 고민이네요 ...........
)
{
    @PrimaryKey(autoGenerate = true) var userIdx : Int = 1  // 0은 로그인에 사용할 거기 때문에 1부터
}
