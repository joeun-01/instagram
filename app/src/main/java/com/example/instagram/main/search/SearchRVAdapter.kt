package com.example.instagram.main.search


import android.annotation.SuppressLint
import android.util.Log
import com.example.instagram.databinding.ItemSearchimgBinding


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instagram.data.PostDB
import com.example.instagram.data.UserDB

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class SearchRVAdapter(private val myInfo : UserDB?, private val myUid : String?): RecyclerView.Adapter<SearchRVAdapter.ViewHolder>() {

    private val post = arrayListOf<PostDB>()

    // 유저 파이어베이스
    private val database = Firebase.database
    private val userRef = database.getReference("user")

    interface MyItemClickListener{
        // click function
        fun onShowSearchtab(post: PostDB)
    }



    private lateinit var mItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener) {
        mItemClickListener = itemClickListener
    }


    @SuppressLint("NotifyDataSetChanged")
    fun addNewPost(post: PostDB) {
        // 리사이클러뷰에 들어갈 게시물 추가
        this.post.add(post)
        notifyDataSetChanged()
        Log.d("SUCCESS-POST", this.post.toString())
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemSearchimgBinding =
            ItemSearchimgBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind(post[position])

        holder.binding.itemSearchImg.setOnClickListener {
            // 게시물 누르면 탐색 탭 나오게
            mItemClickListener.onShowSearchtab(post[position])
        }

    }

    override fun getItemCount(): Int = post.size

    inner class ViewHolder(val binding: ItemSearchimgBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(post: PostDB) {
            var user : UserDB

            userRef.child(post.uid!!).get().addOnSuccessListener {
                user = it.getValue(UserDB::class.java)!!

                // 게시물 내용 연동
                binding.itemSearchImg.setImageResource(post.picture)


                Log.d("SUCCESS-USER", user.toString())

            }.addOnFailureListener {
                Log.d("FAIL-STORY", "유저 데이터를 받아오지 못했습니다")
            }
            }
        }

    }


