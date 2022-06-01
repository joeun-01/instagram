package com.example.instagram.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserTable")
data class User(
    var ID : String,
    var name : String,
    var picture : Int
)
{
    @PrimaryKey(autoGenerate = true) var userIdx : Int = 0
}
