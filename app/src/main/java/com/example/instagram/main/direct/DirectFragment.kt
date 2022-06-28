package com.example.instagram.main.direct

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagram.R
import com.example.instagram.databinding.FragmentDirectBinding
import com.example.instagram.main.MainActivity
import com.example.instagram.main.shop.ShopItem
import com.example.instagram.main.shop.ShopItemFragment
import com.example.instagram.main.shop.ShopRVAdapter


class DirectFragment : Fragment() {

    private lateinit var binding: FragmentDirectBinding
    private var noteDatas = ArrayList<Note>()
    private var directDatas = ArrayList<Direct>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDirectBinding.inflate(inflater, container, false)

        noteDatas.apply {
            add(Note("seohyeonnotstation", R.drawable.profile_ex1, "4시간", "노트 1"))
            add(Note("blue", R.drawable.profile_ex1, "5시간", "노트 2"))
            add(Note("bori", R.drawable.profile_ex1, "4시간", "보리더"))
            add(Note("ally", R.drawable.profile_ex1, "12시간", "뚜비니"))
            add(Note("dobby", R.drawable.profile_ex1, "11시간", "도비"))
        }

        directDatas.apply {
            add(Direct("이서현", R.drawable.profile_ex1, "4시간"))
            add(Direct("김도연", R.drawable.profile_ex1, "5시간"))
            add(Direct("박수빈", R.drawable.profile_ex1, "4시간"))
            add(Direct("정조은", R.drawable.profile_ex1, "12시간"))
        }

        binding.directNoteRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val noteRVAdapter = NoteRVAdapter(noteDatas)
        binding.directNoteRv.adapter = noteRVAdapter


        binding.directMessageRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val directRVAdapter = DirectRVAdapter(directDatas)
        binding.directMessageRv.adapter = directRVAdapter


        return binding.root
    }
}