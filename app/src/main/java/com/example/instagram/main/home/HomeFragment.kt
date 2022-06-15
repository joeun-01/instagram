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
import com.example.instagram.data.Story
import com.example.instagram.databinding.FragmentHomeBinding
import com.example.instagram.databinding.SharePostBottomSheetDialogBinding
import com.example.instagram.databinding.WriteReplyBottomSheetDialogBinding
import com.example.instagram.room.InstagramDatabase
import com.google.android.material.bottomsheet.BottomSheetDialog

class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding

    private lateinit var instaDB : InstagramDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false)

        instaDB = InstagramDatabase.getInstance(requireContext())!!

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        initRecyclerView()
    }

    private fun initRecyclerView() {  // RecycleView 연결
        val storyRVAdapter = StoryRVAdapter(requireContext(), getMyIdx())
        binding.homeFeedStoryRv.adapter = storyRVAdapter
        binding.homeFeedStoryRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        // 스토리에 내 스토리 먼저 추가 후 다른 사람들 스토리 추가
        storyRVAdapter.clearNewStory()

        // 내 스토리를 무조건 맨 앞에 추가하기 위해서 따로 해줌
        if(instaDB.storyDao().getMyStory(getMyIdx()).isEmpty()) {  // 내 스토리가 없는 경우
            // 실제로 스토리가 있는 게 아니라 더미데이터기 때문에 데이터베이스에는 넣지 않음
            storyRVAdapter.addMyDummyStory(Story(getMyIdx(), 0, ""))
        }
        else {  // 내 스토리가 있는 경우
            storyRVAdapter.addNewStory(instaDB.storyDao().getMyStory(getMyIdx()))
        }

        storyRVAdapter.addNewStory(instaDB.storyDao().getOthersStory(getMyIdx()))

        storyRVAdapter.setMyItemClickListener(object : StoryRVAdapter.MyItemClickListener {
            override fun onShowStory(userIdx : Int) {
                showStory(userIdx)
            }
        })

        val postRVAdapter = PostRVAdapter(requireContext(), getMyIdx())
        binding.homeFeedPostRv.adapter = postRVAdapter
        binding.homeFeedPostRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        postRVAdapter.setMyItemClickListener(object : PostRVAdapter.MyItemClickListener {
            override fun onShowComment(postIdx : Int) {
                showAllComment(postIdx)
            }

            override fun onWriteComment(postIdx: Int) {
                writeMyComment(postIdx)
            }

            override fun onShowShare(postIdx: Int) {
                showShareDialog(postIdx)
            }
        })
    }

    private fun getMyIdx(): Int {  // 내 정보를 가져오기 위한 함수
        val userSP = requireActivity().getSharedPreferences("user", MODE_PRIVATE)

        return userSP.getInt("userIdx", 0)
    }

    private fun showStory(userIdx : Int) {  // 스토리 자세히 보기
        val intent = Intent(requireActivity(), StoryActivity::class.java)

        intent.putExtra("userIdx", userIdx)  // userIdx를 넘겨줘서 누구의 스토리를 볼건지 알 수 있게 해줌
        requireActivity().startActivity(intent)  // StoryActivity 실행
    }

    private fun writeMyComment(postIdx: Int) {  // 댓글 달기를 누르면 Bottom Sheet Dialog를 띄움
        val dialog = CommentBottomSheetDialog()

        dialog.show(requireActivity().supportFragmentManager, dialog.tag)

    }

    private fun showAllComment(postIdx : Int) {  // 댓글 모두 보기를 누르면 CommentActivity로 이동
        val postSP = requireActivity().getSharedPreferences("post", MODE_PRIVATE)
        val postEditor = postSP.edit()

        postEditor.putInt("postIdx", postIdx)
        postEditor.apply()

        startActivity(Intent(requireContext(), CommentActivity::class.java))
    }

    private fun showShareDialog(postIdx: Int) {  // 공유 아이콘을 누르면 Bottom Sheet Dialog를 띄움
        val dialog = ShareBottomSheetDialog()

        dialog.show(requireActivity().supportFragmentManager, dialog.tag)
    }

}