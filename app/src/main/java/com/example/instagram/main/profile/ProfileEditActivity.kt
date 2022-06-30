package com.example.instagram.main.profile

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.instagram.data.UserDB
import com.example.instagram.databinding.ActivityProfileEditBinding
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson

class ProfileEditActivity : AppCompatActivity() {
    private lateinit var binding : ActivityProfileEditBinding
    private var gson : Gson = Gson()

    // 파이어베이스
    private val database = Firebase.database
    private val userRef = database.getReference("user")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 사용자 ID 가져오기
        //UserDB(email=, password=, ID=, name=, picture=0)
        var user = gson.fromJson(getMyInfo(), UserDB::class.java)

        binding.editCancelTv.setOnClickListener {
            finish()
        }

        binding.editUseridEt.setText(user.ID)
        binding.editNameEt.setText(user.name)

        binding.editDoneTv.setOnClickListener {
            val name = binding.editNameEt.text.toString()
            val id = binding.editUseridEt.text.toString()

            editName(name)

            if (id.isNotEmpty()){
                editID(id)
            }

            finish()
        }

    }

    // 이름 편집
    private fun editName(name: String){
        val user = gson.fromJson(getMyInfo(), UserDB::class.java)
        user.name = name

        Log.d("New-name ", user.name)

        saveMyInfo(user)

        editDatabase(user)
    }

    // id 편집
    private fun editID(id: String){
        val user = gson.fromJson(getMyInfo(), UserDB::class.java)
        user.ID = id

        Log.d("New-ID ", user.ID)

        saveMyInfo(user)

        editDatabase(user)
    }

    private fun getMyInfo(): String {  // 내 정보를 가져오기 위한 함수
        val userSP = this.getSharedPreferences("user", Context.MODE_PRIVATE)

        return userSP.getString("myInfo", "").toString()
    }

    private fun getMyUid() : String? {  // 내 정보를 가져오기 위한 함수
        val userSP = getSharedPreferences("user", MODE_PRIVATE)

        return userSP.getString("myUid", "")
    }

    private fun saveMyInfo(user : UserDB) {
        val userSP = getSharedPreferences("user", MODE_PRIVATE)
        val userEditor = userSP.edit()

        val userJson = gson.toJson(user)

        userEditor.putString("myInfo", userJson)
        userEditor.apply()
    }

    private fun editDB(origin: String, new: String){
        userRef.child("user").child(origin).setValue(new)
    }

    private fun editDatabase(user: UserDB){
        userRef.child(getMyUid().toString()).setValue(user)
    }
}