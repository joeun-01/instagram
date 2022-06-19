package com.example.instagram.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.instagram.R
import com.example.instagram.data.*
import com.example.instagram.databinding.ActivityMainBinding
import com.example.instagram.main.home.HomeFragment
import com.example.instagram.main.profile.ProfileFragment
import com.example.instagram.main.reels.ReelsFragment
import com.example.instagram.main.search.SearchFragment
import com.example.instagram.main.shop.ShopFragment
import com.example.instagram.room.InstagramDatabase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    private lateinit var instaDB : InstagramDatabase
    private var gson : Gson = Gson()

    private val database = Firebase.database
    private val userRef = database.getReference("user")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        instaDB = InstagramDatabase.getInstance(this)!!


        insertUserDummyData()
//        insertStoryDummyData()
        insertPostDummyData()
        insertCommentDummyData()
        insertReplyDummyData()

        initBottomNavigation()

        // 유저 정보가 잘 들어왔는지 확인
//        Log.d("SUCCESS-MAIN", getMyInfo().toString())

        var user : UserDB
        userRef.child(getMyUid().toString()).get().addOnSuccessListener {
            if(it != null) {
                user = it.getValue(UserDB::class.java)!!
                Log.d("SUCCESS-MAIN", user.toString())
            }
            else {
                Log.d("FAIL-MAIN", "데이터가 존재하지 않습니다")
            }
        }.addOnFailureListener {
            Log.d("FAIL-MAIN", "데이터가 존재하지 않습니다")
        }

    }

    private fun getMyUid() : String? {  // 내 정보를 가져오기 위한 함수
        val userSP = getSharedPreferences("user", MODE_PRIVATE)

        return userSP.getString("myUid", "")
    }

    private fun getMyInfo() : UserDB? {  // 내 정보를 가져오기 위한 함수
        val userSP = getSharedPreferences("user", MODE_PRIVATE)

        val userJson = userSP.getString("myInfo", "")

        return gson.fromJson(userJson, UserDB::class.java)
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

    private fun insertUserDummyData() {

        if(instaDB.userDao().getUsers().size > 1) {
            return
        }

        instaDB.userDao().insert(
            User(instaDB.userDao().getUsers().size + 1, "", "ddobby", "ddobby", R.drawable.profile_ex1, "도비" )
        )

        instaDB.userDao().insert(
            User(instaDB.userDao().getUsers().size + 1, "", "ally", "ally", R.drawable.profile_ex2, "앨리")
        )

        instaDB.userDao().insert(
            User(instaDB.userDao().getUsers().size + 1, "", "blue", "blue", R.drawable.profile_ex3, "블루")
        )

        instaDB.userDao().insert(
            User(instaDB.userDao().getUsers().size + 1, "" , "luna", "luna", R.drawable.profile_ex1, "루나" )
        )

        instaDB.userDao().insert(
            User(instaDB.userDao().getUsers().size + 1, "", "harry", "harry", R.drawable.profile_ex2, "해리")
        )

        instaDB.userDao().insert(
            User(instaDB.userDao().getUsers().size + 1, "", "cocoa", "cocoa", R.drawable.profile_ex3, "코코아")
        )

        instaDB.userDao().insert(
            User(instaDB.userDao().getUsers().size + 1, "", "ark", "ark", R.drawable.profile_ex3, "아크")
        )

        instaDB.userDao().insert(
            User(instaDB.userDao().getUsers().size + 1, "", "ginie", "ginie", R.drawable.profile_ex1, "지니" )
        )

        instaDB.userDao().insert(
            User(instaDB.userDao().getUsers().size + 1, "", "seora", "seora", R.drawable.profile_ex2, "서라")
        )

        instaDB.userDao().insert(
            User(instaDB.userDao().getUsers().size + 1, "", "tama", "tama", R.drawable.profile_ex3, "타마")
        )
    }

    private fun insertStoryDummyData() {

        var mDatabase : DatabaseReference = FirebaseDatabase.getInstance().reference  // 데이터 넣기용

        mDatabase.child("story").child("1").setValue(StoryDB("55Szelu38WUuRboyJe0tfMOt4Wb2", R.drawable.story_dummy1, ""))
        mDatabase.child("story").child("2").setValue(StoryDB("zZJwehvuAobyz74ey93Dr2Qot7v1", R.drawable.story_dummy2, ""))
        mDatabase.child("story").child("3").setValue(StoryDB("953fvgZBVKOWjiMbgDqTPBWiOUe2", R.drawable.story_dummy3, ""))
        mDatabase.child("story").child("4").setValue(StoryDB("GtpScCFisoQqnO8SaTkTqojrMJ62", R.drawable.story_dummy4, ""))
        mDatabase.child("story").child("5").setValue(StoryDB("J1VVdi5H5QUrEkxXuWIsDamQqLE2", R.drawable.story_dummy5, ""))
        mDatabase.child("story").child("6").setValue(StoryDB("kQMcCspPocXh9I0Dw85oNq497CW2", R.drawable.story_dummy6, ""))

    }

    private fun insertPostDummyData() {

        if(instaDB.postDao().getPosts().isNotEmpty()) {
            return
        }

        instaDB.postDao().insert(
            Post(2, R.drawable.profile_ex1, "얼음 깨기 너무 재밌었다!", "", 0)
        )

        instaDB.postDao().insert(
            Post(5, R.drawable.profile_ex2, "한강 최고ㅎㅎ", "", 0)
        )

        instaDB.postDao().insert(
            Post(1, R.drawable.profile_ex3, "차가 마시고 싶은 날...", "", 0)
        )
    }

    private fun insertCommentDummyData() {

        if(instaDB.CommentDao().getComments().size > 1) {
            return
        }

        instaDB.CommentDao().insert(
            Comment(1, 1, "댓글 예시", "", 0)
        )

        instaDB.CommentDao().insert(
            Comment(2, 2, "댓글 예시", "", 0)
        )

        instaDB.CommentDao().insert(
            Comment(3, 3, "댓글 예시", "", 0)
        )
    }

    private fun insertReplyDummyData() {
        if(instaDB.CommentDao().getReplies().isNotEmpty()) {
            return
        }

        instaDB.CommentDao().insertReply(
            Reply(5, 3, 3, "헐 대박이다", "", 0)
        )

        instaDB.CommentDao().insertReply(
            Reply(6, 3, 3, "진짜?", "", 0)
        )

        instaDB.CommentDao().insertReply(
            Reply(1, 2, 2, "와~~~", "", 0)
        )

        instaDB.CommentDao().insertReply(
            Reply(5, 1, 1, "에엥", "", 0)
        )

        instaDB.CommentDao().insertReply(
            Reply(5, 1, 1, "대박이다", "", 0)
        )
    }

}