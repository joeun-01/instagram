package com.example.instagram.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.instagram.*
import com.example.instagram.data.Post
import com.example.instagram.databinding.ActivityMainBinding
import com.example.instagram.main.home.HomeFragment
import com.example.instagram.main.profile.ProfileFragment
import com.example.instagram.main.reels.ReelsFragment
import com.example.instagram.main.search.SearchFragment
import com.example.instagram.main.shop.ShopFragment
import com.example.instagram.room.InstagramDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    private lateinit var instaDB : InstagramDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        instaDB = InstagramDatabase.getInstance(this)!!


        initBottomNavigation()

        insertPostDummyData()
    }

    private fun initBottomNavigation(){

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, HomeFragment())
            .commitAllowingStateLoss()

        binding.mainBnv.setOnItemSelectedListener{
            item ->
            when (item.itemId) {

                R.id.homeFragment -> {  // 홈 화면 실행
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, HomeFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.searchFragment -> {  // 검색 화면 실행
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, SearchFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.reelsFragment -> {  // 검색 화면 실행
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, ReelsFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.shopFragment -> {  // 보관함 화면 실행
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, ShopFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.profileFragment -> {  // 프로필필 화면 실행
                   supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, ProfileFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

    private fun insertPostDummyData() {

        instaDB.postDao().insert(
            Post("bori", R.drawable.profile_ex1, "즐거운 하루", false)
        )

        instaDB.postDao().insert(
            Post("ally", R.drawable.profile_ex2, "즐거운 하루", false)
        )

        instaDB.postDao().insert(
            Post("dobby", R.drawable.profile_ex3, "즐거운 하루", false)
        )
    }
}