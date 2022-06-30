package com.example.instagram.main.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.instagram.R
import com.example.instagram.data.UserDB
import com.example.instagram.databinding.FragmentProfileBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson

class ProfileFragment : Fragment() {

    private lateinit var binding : FragmentProfileBinding
    private var gson : Gson = Gson()

    private var tab = arrayListOf(R.drawable.ic_grid, R.drawable.ic_tag)

    // 파이어베이스
    private val database = Firebase.database
    private val storyRef = database.getReference("story")
    private val postRef = database.getReference("post")
    private val userRef = database.getReference("user")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProfileBinding.inflate(inflater, container, false)

        Log.d("Uid " , getMyUid())
        //GtpScCFisoQqnO8SaTkTqojrMJ62
        Log.d("info " , getMyInfo())
        //{"ID":"dobby","email":"dobby@naver.com","name":"dobby","password":"dobby11","picture":2131165283}

        Log.d("CHECKITOUT", getMyInfo())

        // ViewPager 연결
        val profileVPAdapter = ProfileVPAdapter(this)
        binding.profilePostVp.adapter = profileVPAdapter

        // ViewPager와 TabLayout 연결
        TabLayoutMediator(binding.profilePostTb, binding.profilePostVp) {
            tab, position ->
            tab.setIcon(this.tab[position])
        }.attach()

        binding.profileHighlightDownIv.setOnClickListener {
            setTextStatus(true)
        }
        binding.profileHighlightUpIv.setOnClickListener {
            setTextStatus(false)
        }

        binding.profileEditTv.setOnClickListener {
            changeEditFragment()
        }

        binding.profileAddIv.setOnClickListener {
            showDialogCreate()
        }
        binding.profileListIv.setOnClickListener {
            showDialogList()
        }


        return binding.root
    }

    override fun onStart() {
        super.onStart()
        // 사용자 ID 가져오기
        val user = gson.fromJson(getMyInfo(), UserDB::class.java)
        Log.d("Profile ", user.toString())
//        Log.d("User-ID ", user.ID)
//        Log.d("User-name ", user.name)

        binding.profilePictureIv.setImageResource(user.picture)
        binding.profileNameTv.text = user.ID

        if(user.name.isNotEmpty()) {
            binding.profileRealnameTv.visibility = View.VISIBLE
            binding.profileRealnameTv.text = user.name
        }
        else {
            binding.profileRealnameTv.text = user.name
            binding.profileRealnameTv.visibility = View.GONE
        }

    }

    private fun showDialogCreate(){
        val create = CreateBottomSheetDialog()

        create.show(requireActivity().supportFragmentManager, create.tag)
    }

    private fun showDialogList(){
        val dialog = ListBottomSheetDialog()

        dialog.show(requireActivity().supportFragmentManager, dialog.tag)
    }

    private fun getMyUid(): String {  // 내 정보를 가져오기 위한 함수
        val userSP = requireActivity().getSharedPreferences("user", Context.MODE_PRIVATE)

        return userSP.getString("myUid", "").toString()
    }

    private fun getMyInfo(): String {  // 내 정보를 가져오기 위한 함수
        val userSP = requireActivity().getSharedPreferences("user", Context.MODE_PRIVATE)

        return userSP.getString("myInfo", "").toString()
    }

    private fun setTextStatus(isVisible: Boolean){
        if (isVisible){
            binding.profileHighlightTextTv.visibility = View.VISIBLE
            binding.profileHighlightDownIv.visibility = View.GONE
            binding.profileHighlightUpIv.visibility = View.VISIBLE
            binding.profileHighlightNewIv.visibility = View.VISIBLE
            binding.profileHighlightNewTv.visibility = View.VISIBLE
        }
        else{
            binding.profileHighlightTextTv.visibility = View.GONE
            binding.profileHighlightDownIv.visibility = View.VISIBLE
            binding.profileHighlightUpIv.visibility = View.GONE
            binding.profileHighlightNewIv.visibility = View.GONE
            binding.profileHighlightNewTv.visibility = View.GONE
        }
    }

    private fun changeEditFragment(){
        startActivity(Intent(activity, ProfileEditActivity::class.java))
    }

}