package com.example.instagram.main.search

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instagram.R
import com.example.instagram.data.PostDB
import com.example.instagram.data.UserDB
import com.example.instagram.databinding.ItemSearchimgBinding
import com.example.instagram.databinding.ItemSearchtabBinding
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SearchTabRVAdapter(private val myInfo : UserDB?, private val myUid : String?) : RecyclerView.Adapter<SearchTabRVAdapter.ViewHolder>() {

    private val post = arrayListOf<PostDB>()

    // 유저 파이어베이스
    private val database = Firebase.database
    private val userRef = database.getReference("user")


    @SuppressLint("NotifyDataSetChanged")
    fun addNewSearchtab(post: PostDB) {
        // 리사이클러뷰에 들어갈 post 추가
        this.post.add(post)
        notifyDataSetChanged()
        Log.d("SUCCESS-STORY", this.post.toString())
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemSearchtabBinding =
            ItemSearchtabBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)


        return ViewHolder(binding)  // ViewHolder를 생성
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // 해당 position에 대한 데이터를 binding
        holder.bind(post[position])

    }


    override fun getItemCount(): Int = post.size

    inner class ViewHolder(val binding: ItemSearchtabBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(post: PostDB) {

            var user: UserDB

            val userID=Bundle().getString("userID")
            val picture=Bundle().getInt("picture")

            userRef.child(post.uid!!).get().addOnSuccessListener {
                user = it.getValue(UserDB::class.java)!!


                // 게시물 내용 연동
                binding.itemSearchtabIdTv.text = userID
                binding.itemSearchtabPostIdTv.text = userID
                binding.itemSearchtabPostIv.setImageResource(picture)

                val postText = post.text  // 텍스트 가져옴
                binding.itemSearchtabPostTextTv.text = postText.toString()


            }
        }

    }
}