package com.example.instagram.room

import androidx.room.*
import com.example.instagram.data.Comment
import com.example.instagram.data.Reply

@Dao
interface CommentDao {

    @Insert
    fun insert(comment: Comment)

    @Delete
    fun delete(comment: Comment)

    @Update
    fun update(comment: Comment)

    @Query("SELECT * FROM CommentTable")
    fun getComments() : List<Comment>

    @Query("SELECT * FROM CommentTable WHERE postIdx = :postIdx")
    fun getPostComments(postIdx : Int) : List<Comment>

    // 답글 관련 다오
    @Query("SELECT * FROM ReplyTable")
    fun getReplies() : List<Reply>

    @Query("SELECT * FROM ReplyTable WHERE postIdx = :postIdx")
    fun getPostReplies(postIdx: Int) : List<Reply>

    // 댓글에 달린 답글 가져오기
    @Query("SELECT * FROM ReplyTable WHERE commentIdx = :commentIdx")
    fun getCommentReply(commentIdx : Int) : List<Reply>

    // 답글 insert
    @Insert
    fun insertReply(reply: Reply)
}