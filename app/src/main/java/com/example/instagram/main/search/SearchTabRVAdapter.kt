package com.example.instagram.main.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instagram.data.PostDB
import com.example.instagram.data.SearchDB
import com.example.instagram.data.UserDB
import com.example.instagram.databinding.ItemSearchtabBinding
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SearchTabRVAdapter(private val myInfo : UserDB?, private val myUid : String?) : RecyclerView.Adapter<SearchTabRVAdapter.ViewHolder>() {

    private val search = arrayListOf<SearchDB>()

    // 유저 파이어베이스
    private val database = Firebase.database
    private val userRef = database.getReference("user")


    @SuppressLint("NotifyDataSetChanged")
    fun addNewSearchtab(search: SearchDB) {
        // 리사이클러뷰에 들어갈 post 추가
        this.search.add(search)
        notifyDataSetChanged()
        Log.d("SUCCESS-STORY", this.search.toString())
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemSearchtabBinding =
            ItemSearchtabBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)


        return ViewHolder(binding)  // ViewHolder를 생성
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // 해당 position에 대한 데이터를 binding
        holder.bind(search[position])


                }


    override fun getItemCount(): Int = search.size

    inner class ViewHolder(val binding: ItemSearchtabBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(search: SearchDB) {

            var user: UserDB

            userRef.child(search.uid!!).get().addOnSuccessListener {
                user = it.getValue(UserDB::class.java)!!

//                val userID=Bundle().getString("userID")
//                val picture=Bundle().getInt("picture")

               // 게시물 내용 연동
                binding.itemSearchtabIdTv.text =user.ID
                binding.itemSearchtabPostIdTv.text =user.ID
                binding.itemSearchtabPostIv.setImageResource(search.picture)

                val searchText = search.text  // 텍스트 가져옴
                binding.itemSearchtabPostTextTv.text = searchText.toString()


            }
        }

    }}
