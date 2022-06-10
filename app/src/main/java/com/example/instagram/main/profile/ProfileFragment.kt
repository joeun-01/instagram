package com.example.instagram.main.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.instagram.R
import com.example.instagram.databinding.FragmentProfileBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayoutMediator

class ProfileFragment : Fragment() {

    private lateinit var binding : FragmentProfileBinding

    private var tab = arrayListOf(R.drawable.ic_grid, R.drawable.ic_tag)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProfileBinding.inflate(inflater, container, false)

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


        val createView = layoutInflater.inflate(R.layout.fragment_create, null)
        val create = BottomSheetDialog(requireContext())

        create.setContentView(createView)
        create.setCanceledOnTouchOutside(true)

        binding.profileAddIv.setOnClickListener {
            create.show()
        }



        val dialogView = layoutInflater.inflate(R.layout.fragment_list, null)
        val dialog = BottomSheetDialog(requireContext())
//
//        val layout = dialogView.layoutParams
//        layout.height =

        dialog.setContentView(dialogView)

        binding.profileListIv.setOnClickListener {
            dialog.show()
        }
//
//        val delete = dialogView.findViewById<LinearLayout>(R.id.bottomDialog_delete)  // dialog custom 삭제 화면에 있는 종료 버튼
//
//        dialog.setOnDismissListener {
//        }
//
//        delete.setOnClickListener {
//            // dialog를 종료하면서 실행할 것들
//
//            dialog.dismiss()  // dialog 종료
//        }


        return binding.root
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