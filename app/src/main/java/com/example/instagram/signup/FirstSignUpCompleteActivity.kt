package com.example.instagram.signup

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.instagram.data.User
import com.example.instagram.data.UserDB
import com.example.instagram.databinding.ActivityFirstSignupCompleteBinding
import com.example.instagram.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson


class FirstSignUpCompleteActivity : AppCompatActivity() {

    lateinit var binding : ActivityFirstSignupCompleteBinding

    private val gson : Gson = Gson()
    private var auth : FirebaseAuth? = null

    private lateinit var mDatabase : DatabaseReference

    private var userList = arrayListOf<UserDB>()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFirstSignupCompleteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth  // 회원가입용
        mDatabase = FirebaseDatabase.getInstance().reference  // 데이터 넣기용

        // 유저 정보 가져오기
        val userSP = getSharedPreferences("user", MODE_PRIVATE)
        val userJson = userSP.getString("userInfo", "")

        val user = gson.fromJson(userJson, UserDB::class.java)

        binding.firstSignupCompleteTitleTv.text = user.ID + "님으로 가입하시겠어요?"

        // 로그인 화면 연결
        binding.firstSignupCompleteLoginTv.setOnClickListener {
            startLoginActivity()
        }

        // 값이 바뀌면 DB에서 값을 받아옴
        // 한 번만 호출하는 게 중요!!!!
//        readUser()

        binding.firstSignupCompleteNextTv.setOnClickListener {
            createAccount(user)
        }
    }

    private fun createAccount(user: UserDB) {
        auth?.createUserWithEmailAndPassword(user.email, user.password)?.addOnCompleteListener(this) {
                task ->
            if (task.isSuccessful) {
                putIntoDatabase(user)
                Log.d("SUCCESS-MAIN", userList.toString())

                startLoginActivity()  // 회원가입이 끝나면 로그인 화면으로

                Toast.makeText(this, "계정 생성 완료", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "계정 생성 실패", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun putIntoDatabase(user : UserDB) {
        mDatabase.child("user").child((auth!!.uid.toString())).setValue(user)
    }

    private fun startLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
    }
}