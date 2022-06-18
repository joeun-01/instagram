package com.example.instagram.main.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.instagram.R
import com.example.instagram.databinding.FragmentProfileBinding
import com.example.instagram.main.home.ShareBottomSheetDialog
import com.example.instagram.room.InstagramDatabase
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayoutMediator

class ProfileFragment : Fragment() {

    private lateinit var binding : FragmentProfileBinding
    private lateinit var instaDB : InstagramDatabase

    private var tab = arrayListOf(R.drawable.ic_grid, R.drawable.ic_tag)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProfileBinding.inflate(inflater, container, false)
        instaDB = InstagramDatabase.getInstance(requireContext())!!

        // 로그인한 사용자 ID 연동
        binding.profileNameTv.text = instaDB.userDao().getUserID(getMyIdx())

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

        binding.profileNameTv.text = instaDB.userDao().getUserID(getMyIdx())
    }

    private fun showDialogCreate(){
        val create = CreateBottomSheetDialog()

        create.show(requireActivity().supportFragmentManager, create.tag)
    }

    private fun showDialogList(){
        val dialog = ListBottomSheetDialog()

        dialog.show(requireActivity().supportFragmentManager, dialog.tag)
    }

    private fun getMyIdx(): Int {  // 내 정보를 가져오기 위한 함수
        val userSP = requireActivity().getSharedPreferences("user", Context.MODE_PRIVATE)

        return userSP.getInt("userIdx", 0)
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