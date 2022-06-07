package com.example.instagram.main.home

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagram.R
import com.example.instagram.databinding.FragmentHomeBinding
import com.example.instagram.databinding.WriteReplyBottomSheetDialogBinding
import com.example.instagram.room.InstagramDatabase
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.zip.Inflater

class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding
    private lateinit var dialogBinding : WriteReplyBottomSheetDialogBinding

    private lateinit var instaDB : InstagramDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        dialogBinding = WriteReplyBottomSheetDialogBinding.inflate(inflater, container, false)

        instaDB = InstagramDatabase.getInstance(requireContext())!!

        // 댓글 달기 위한 준비랄까요...........
        dialogBinding.writeCommentEt.addTextChangedListener(object :TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                dialogBinding.writeCommentEt.hint = "이렇게 바꿔보자"
                // 입력이 시작되기 전에 작동
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // 입력이 시작되면 작동
                dialogBinding.writeCommentEnterTv.setTextColor(Color.parseColor("0C88FA"))
                // 왜 안바뀌지???????
                /// 왜 ??????????????????????????????????????????????????????????????????????????????/
                // ????????????????????????????????????
            }

            override fun afterTextChanged(p0: Editable?) {
                // 입력이 끝난 후에 작동
                // 여기에 comment 값을 넣고
                dialogBinding.writeCommentEt.hint = "다 썼다!"
            }
        })

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        initRecyclerView()
    }

    private fun initRecyclerView() {  // RecycleView 연결
        val storyRVAdapter = StoryRVAdapter(requireContext())
        binding.homeFeedStoryRv.adapter = storyRVAdapter
        binding.homeFeedStoryRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        val postRVAdapter = PostRVAdapter(requireContext(), getMyIdx())
        binding.homeFeedPostRv.adapter = postRVAdapter
        binding.homeFeedPostRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        postRVAdapter.setMyItemClickListener(object : PostRVAdapter.MyItemClickListener {
            override fun showComment(postIdx : Int) {
                showAllComment(postIdx)
            }

            override fun writeComment(postIdx: Int) {
                writeMyComment(postIdx)
            }
        })
    }

    private fun getMyIdx(): Int {  // 내 정보를 가져오기 위한 함수
        val userSP = requireActivity().getSharedPreferences("user", MODE_PRIVATE)

        return userSP.getInt("userIdx", 0)
    }

    private fun writeMyComment(postIdx: Int) {  // 댓글 달기를 누르면 Bottom Sheet Dialog를 띄움
        val dialogView = layoutInflater.inflate(R.layout.write_reply_bottom_sheet_dialog, null)
        val dialog = BottomSheetDialog(requireContext())

        dialog.setContentView(dialogView)

        dialog.show()

        dialogBinding.writeCommentEnterTv.setOnClickListener {

        }
    }

    private fun showAllComment(postIdx : Int) {  // 댓글 모두 보기를 누르면 CommentActivity로 이동
        val postSP = requireActivity().getSharedPreferences("post", MODE_PRIVATE)
        val postEditor = postSP.edit()

        postEditor.putInt("postIdx", postIdx)
        postEditor.apply()

        startActivity(Intent(requireContext(), CommentActivity::class.java))
    }

}