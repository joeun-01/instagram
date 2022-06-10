package com.example.instagram.room

import androidx.room.*
import com.example.instagram.data.Story

@Dao
interface StoryDao {

    @Insert
    fun insert(story: Story)

    @Delete
    fun delete(story: Story)

    @Update
    fun update(story: Story)

    @Query("SELECT * FROM StoryTable")
    fun getStories() : List<Story>

    // 내 스토리 빼고 가져오기
    @Query("SELECT * FROM StoryTable WHERE NOT userIdx = :myIdx")
    fun getOthersStory(myIdx : Int) : List<Story>

    @Query("SELECT * FROM StoryTable WHERE userIdx = :userIdx")
    fun getUserStory(userIdx : Int) : Story
}