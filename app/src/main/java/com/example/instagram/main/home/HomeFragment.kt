package com.example.instagram.main.home

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagram.data.PostDB
import com.example.instagram.data.StoryDB
import com.example.instagram.data.UserDB
import com.example.instagram.databinding.FragmentHomeBinding
import com.example.instagram.room.InstagramDatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson

class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding

    private lateinit var instaDB : InstagramDatabase
    private var gson : Gson = Gson()

    // 파이어베이스
    private val database = Firebase.database
    private val storyRef = database.getReference("story")
    private val postRef = database.getReference("post")

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

    private fun initRecyclerView() {
        // 스토리 리사이클러뷰 연결
        val storyRVAdapter = StoryRVAdapter(getMyUid())
        binding.homeFeedStoryRv.adapter = storyRVAdapter
        binding.homeFeedStoryRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        // 스토리 데이터 받아오기
        storyRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                storyRVAdapter.clearNewStory()

                if (snapshot.exists()){
                    var exist = false  // 내 스토리 존재 여부 확인을 위한 변수

                    for (storySnapshot in snapshot.children){
                        val getData = storySnapshot.getValue(StoryDB::class.java)

                        if (getData != null) {
                            if(getData.uid == getMyUid()) {
                                // 내가 올린 스토리가 있으면 맨 처음에 뜨도록 추가
                                storyRVAdapter.addNewStoryToFirst(getData)
                                exist = true  // 존재 여부를 확인
                            }
                            // 내가 올린 스토리가 아니면 순서 상관 없이 추가
                            storyRVAdapter.addNewStory(getData)
                        }

//                        Log.d("SUCCESS", getData.toString())
                    }

                    if(!exist) {
                        // 내가 올린 스토리가 존재하지 않으면 더미데이터를 추가
                        storyRVAdapter.addNewStoryToFirst(StoryDB(getMyUid(), 0, ""))
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("FAIL", "데이터를 불러오지 못했습니다")
            }
        })

        storyRVAdapter.setMyItemClickListener(object : StoryRVAdapter.MyItemClickListener {
            override fun onShowStory(story: StoryDB) {
                showStory(story)
            }
        })

        // 게시물 리사이클러뷰 연결
        val postRVAdapter = PostRVAdapter(requireContext(), getMyInfo())
        binding.homeFeedPostRv.adapter = postRVAdapter
        binding.homeFeedPostRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        // 게시물 데이터 받아오기
        postRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                postRVAdapter.clearNewPost()

                if (snapshot.exists()){
                    for (postSnapshot in snapshot.children){
                        val getData = postSnapshot.getValue(PostDB::class.java)

                        if (getData != null) {
                            postRVAdapter.addNewPost(getData)
                        }

                        Log.d("SUCCESS", getData.toString())
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("FAIL", "데이터를 불러오지 못했습니다")
            }
        })

        postRVAdapter.setMyItemClickListener(object : PostRVAdapter.MyItemClickListener {
            override fun onShowComment(post: PostDB) {
                showAllComment(post)
            }

            override fun onWriteComment(postIdx: Int) {
                writeMyComment(postIdx)
            }

            override fun onShowShare(postIdx: Int) {
                showShareDialog(postIdx)
            }
        })
    }

    private fun getMyUid() : String? {  // 내 정보를 가져오기 위한 함수
        val userSP = requireActivity().getSharedPreferences("user", MODE_PRIVATE)

        return userSP.getString("myUid", "")
    }

    private fun getMyInfo() : UserDB? {  // 내 정보를 가져오기 위한 함수
        val userSP = requireActivity().getSharedPreferences("user", MODE_PRIVATE)

        val userJson = userSP.getString("myInfo", "")

        return gson.fromJson(userJson, UserDB::class.java)
    }

    private fun showStory(story: StoryDB) {  // 스토리 자세히 보기
        val intent = Intent(requireActivity(), StoryActivity::class.java)

        intent.putExtra("userID", story.uid)  // userIdx를 넘겨줘서 누구의 스토리를 볼건지 알 수 있게 해줌
        intent.putExtra("story", story.picture)
        intent.putExtra("date", story.date)
        requireActivity().startActivity(intent)  // StoryActivity 실행
    }

    private fun writeMyComment(postIdx: Int) {  // 댓글 달기를 누르면 Bottom Sheet Dialog를 띄움
        val dialog = CommentBottomSheetDialog()

        dialog.show(requireActivity().supportFragmentManager, dialog.tag)

    }

    private fun showAllComment(post : PostDB) {  // 댓글 모두 보기를 누르면 CommentActivity로 이동
        val postSP = requireActivity().getSharedPreferences("post", MODE_PRIVATE)
        val postEditor = postSP.edit()

        val postJson = gson.toJson(post)

        postEditor.putString("postInfo", postJson)
        postEditor.apply()

        startActivity(Intent(requireContext(), CommentActivity::class.java))
    }

    private fun showShareDialog(postIdx: Int) {  // 공유 아이콘을 누르면 Bottom Sheet Dialog를 띄움
        val dialog = ShareBottomSheetDialog()

        dialog.show(requireActivity().supportFragmentManager, dialog.tag)
    }

}