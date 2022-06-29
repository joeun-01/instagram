package com.example.instagram.main.late_signup

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.instagram.data.UserDB
import com.example.instagram.databinding.ActivityAnotherSignupCompleteBinding
import com.example.instagram.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson

class SignUpCompleteActivity : AppCompatActivity() {
    lateinit var binding : ActivityAnotherSignupCompleteBinding

    private val gson : Gson = Gson()

    private var auth : FirebaseAuth? = null
    private lateinit var mDatabase : DatabaseReference

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAnotherSignupCompleteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth  // 회원가입용
        mDatabase = FirebaseDatabase.getInstance().reference  // 데이터 넣기용

        // 여태까지 저장한 유저 정보 가져오기
        val userSP = getSharedPreferences("user", MODE_PRIVATE)
        var userJson = userSP.getString("userInfo", "")

        val user = gson.fromJson(userJson, UserDB::class.java)

        binding.signupCompleteWelcomeTv.text = user.ID + "님, Instagram에\n 오신 것을 환영합니다"

        binding.signupCompleteCompleteBtn.setOnClickListener {
            createAccount(user)
        }
    }

    private fun createAccount(user: UserDB) {
        auth?.createUserWithEmailAndPassword(user.email, user.password)?.addOnCompleteListener(this) {
                task ->
            if (task.isSuccessful) {
                putIntoDatabase(user)

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