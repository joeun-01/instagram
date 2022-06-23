package com.example.instagram.main.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instagram.data.User
import com.example.instagram.databinding.ItemSharePostBinding
import com.example.instagram.room.InstagramDatabase

class ShareRVAdapter(context : Context, private val myIdx : Int) : RecyclerView.Adapter<ShareRVAdapter.ViewHolder>() {
    private val instaDB = InstagramDatabase.getInstance(context)!!
    private var user = ArrayList<User>()

    interface MyItemClickListener{

    }

    private lateinit var mItemClickListener: PostRVAdapter.MyItemClickListener
    fun setMyItemClickListener(itemClickListener : PostRVAdapter.MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    fun addMyInfo(user : User) {
        // 리사이클러뷰에 들어갈 스토리 초기화
        this.user.add(user)
    }

    fun addOthersInfo(user : List<User>) {
        // 리사이클러뷰에 들어갈 스토리 초기화
        this.user.addAll(user)
    }

    fun clearInfo() {
        this.user.clear()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ShareRVAdapter.ViewHolder {
        val binding : ItemSharePostBinding = ItemSharePostBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)  // ViewHolder를 생성
    }

    override fun onBindViewHolder(holder: ShareRVAdapter.ViewHolder, position: Int) {
        holder.bind(user[position])
    }

    override fun getItemCount(): Int = user.size

    inner class ViewHolder(val binding : ItemSharePostBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user : User) {
            // 유저 정보 연결
            binding.sharePostProfileIv.setImageResource(user.picture)

            if(user.name != "") {  // 따로 설정한 이름이 있는 경우
                // 이름, 아이디 둘 다 보여주기
                binding.sharePostNameTv.text = user.name
                binding.sharePostIdTv.text = user.ID

                binding.sharePostIdTv.visibility = View.VISIBLE
            }
            else {
                // 아이디만 보여주기
                binding.sharePostNameTv.text = user.ID

                binding.sharePostIdTv.visibility = View.GONE
            }
        }
    }
}