package com.example.instagram.main.shop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagram.R
import com.example.instagram.databinding.FragmentCollectionBinding
import com.example.instagram.databinding.FragmentEditorBinding
import com.example.instagram.main.MainActivity


class EditorFragment : Fragment() {

    private lateinit var binding: FragmentEditorBinding
    private var editors = ArrayList<Editor>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditorBinding.inflate(inflater, container, false)


        binding.editorRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val editorRVAdapter = EditorRVAdapter(editors)
        binding.editorRv.adapter = editorRVAdapter

        /*guideRVAdapter.setMyItemClickListener(object : GuideRVAdapter.MyItemClickListener {
            override fun onItemClick(){
                (context as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frm, ShopItemFragment()).commitAllowingStateLoss()
            }
        })*/

        binding.editorWishIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, WishlistFragment()).addToBackStack(null).commitAllowingStateLoss()
        }

        binding.backIb.setOnClickListener {
            (context as MainActivity).onBackPressed()
        }

        editors.apply {
            add(Editor(R.drawable.editor,"android.official", "에디터"))
            add(Editor(R.drawable.editor,"android.official", "에디터"))
            add(Editor(R.drawable.editor,"android.official", "에디터"))
            add(Editor(R.drawable.editor,"android.official", "에디터"))
        }


        return binding.root
    }
}