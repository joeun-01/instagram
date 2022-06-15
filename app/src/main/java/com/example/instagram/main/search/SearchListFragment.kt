package com.example.instagram.main.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import com.example.instagram.R
import com.example.instagram.databinding.FragmentSearchlistBinding
import com.example.instagram.main.MainActivity

class SearchListFragment : Fragment(){

    private lateinit var binding: FragmentSearchlistBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    {
        binding = FragmentSearchlistBinding.inflate(inflater, container, false)

        //검색 취소
        binding.searchlistCloseTv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, SearchFragment()).commitAllowingStateLoss()
        }

        //검색바 누르고 키보드의 검색버튼 누르면 검색결과 fragment로 전환
        binding.searchlistSearchbarEt.setOnEditorActionListener { v, actionId, event ->
            var handled = false

            if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                (context as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frm, SearchRstFragment()).commitAllowingStateLoss()

                     //텍스트도 같이 넘어가게끔, 아직 구현 못했어여
                     //var resultTxt=binding.searchlistSearchbarEt.editableText.toString()
                     //binding.searchlistSearchbarEt.editableText=resultTxt

                handled = true
            }
            handled

        }

        return binding.root

    }
}