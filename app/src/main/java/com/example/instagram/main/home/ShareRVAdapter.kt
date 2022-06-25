package com.example.instagram.main.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instagram.data.UserDB
import com.example.instagram.databinding.ItemSharePostBinding

class ShareRVAdapter(private val myInfo : UserDB) : RecyclerView.Adapter<ShareRVAdapter.ViewHolder>() {
    private var user = ArrayList<UserDB>()

    interface MyItemClickListener{
        // click listener
    }

    private lateinit var mItemClickListener: PostRVAdapter.MyItemClickListener
    fun setMyItemClickListener(itemClickListener : PostRVAdapter.MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addUserInfo(user : UserDB) {
        // 리사이클러뷰에 들어갈 스토리 초기화
        this.user.add(user)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addMyInfo(user : UserDB) {
        // 리사이클러뷰에 들어갈 스토리 초기화
        this.user.add(0, user)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearInfo() {
        this.user.clear()
        notifyDataSetChanged()
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
        fun bind(user : UserDB) {
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

            if(user.ID == myInfo.ID) {  // 내 정보의 경우에만 공유, 아니면 체크박스
                binding.sharePostShareBtn.visibility = View.VISIBLE
                binding.sharePostCheckboxCb.visibility = View.GONE
            }
            else {
                binding.sharePostShareBtn.visibility = View.GONE
                binding.sharePostCheckboxCb.visibility = View.VISIBLE
            }
        }
    }
}