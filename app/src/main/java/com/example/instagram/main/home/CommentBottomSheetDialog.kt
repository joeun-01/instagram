package com.example.instagram.main.home

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.instagram.databinding.WriteReplyBottomSheetDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CommentBottomSheetDialog : BottomSheetDialogFragment() {
    lateinit var binding : WriteReplyBottomSheetDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = WriteReplyBottomSheetDialogBinding.inflate(inflater, container, false)

        // 댓글 달기
        binding.writeCommentEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.writeCommentEt.hint = "이렇게 바꿔보자"
                // 입력이 시작되기 전에 작동
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // 입력이 시작되면 작동
                binding.writeCommentEnterTv.setTextColor(Color.parseColor("#0C88FA"))
            }

            override fun afterTextChanged(p0: Editable?) {
                // 입력이 끝난 후에 작동
                // 여기에 comment 값을 넣고
                binding.writeCommentEt.hint = "다 썼다!"
            }
        })

        return binding.root
    }

    private fun getMyIdx(): Int {  // 내 정보를 가져오기 위한 함수
        val userSP = requireActivity().getSharedPreferences("user", Context.MODE_PRIVATE)

        return userSP.getInt("userIdx", 0)
    }
}