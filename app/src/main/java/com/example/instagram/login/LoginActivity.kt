package com.example.instagram.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.instagram.databinding.ActivityLoginBinding
import com.example.instagram.main.MainActivity
import com.example.instagram.room.InstagramDatabase
import com.example.instagram.signup.FirstSignUpStep1Activity
import com.google.firebase.auth.FirebaseAuth


class LoginActivity: AppCompatActivity() {

    lateinit var binding : ActivityLoginBinding

    private lateinit var userDB : InstagramDatabase
    private var firebaseAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userDB = InstagramDatabase.getInstance(this)!!
        firebaseAuth = FirebaseAuth.getInstance()

        binding.loginLoginBtn.setOnClickListener {
            // 로그인 버튼을 누르면 로그인 프로세스 진행
            login()
        }

        binding.loginSignupTv.setOnClickListener {
            // 회원가입을 원하면 회원가입 창으로
            startActivity(Intent(this, FirstSignUpStep1Activity::class.java))
        }
    }

    private fun login() {
        val id = binding.loginIdEt.text.toString()
        val password = binding.loginPasswordEt.text.toString()

        // 아이디나 비밀번호 중 하나라도 비어있으면 로그인이 되지 않도록 return
        if(id.isEmpty()) {
            Toast.makeText(this, "아이디를 입력하세요",Toast.LENGTH_SHORT).show()
            return
        }
        if(password.isEmpty()) {
            Toast.makeText(this, "비밀번호를 입력하세요",Toast.LENGTH_SHORT).show()
            return
        }

//        Log.d("입력한 아이디, 비밀번호", "$id, $password")
//        Log.d("저장한 정보", userDB.userDao().getUsers().toString())
//        Log.d("저장한 정보", userDB.userDao().login(id).toString())

        // roomDB 이용한 로그인인
//       if(password == userDB.userDao().login(id)) {
//            // 자동로그인, 유저 정보를 불러오기 위한 유저 정보 저장
//            val userSP = getSharedPreferences("user", MODE_PRIVATE)
//            val userEditor = userSP.edit()
//
//            userEditor.putInt("userIdx", userDB.userDao().getUserIdx(id))
//            userEditor.apply()
//
//            Log.d("Login User id ", userDB.userDao().getUser(userDB.userDao().getUserIdx(id)).toString())
//
//            // 로그인이 완료되면 홈 화면으로
//            startActivity(Intent(this, MainActivity::class.java))
//        }

        // 파이어베이스를 이용한 로그인
        firebaseAuth!!.signInWithEmailAndPassword(id, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "로그인 오류", Toast.LENGTH_SHORT).show()
            }
        }
    }

}