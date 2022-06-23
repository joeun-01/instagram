package com.example.instagram.data

data class CommentDB(
    // 댓글 정보 저장
    var uid : String = "",  // 댓글 단 사람
    var postIdx : Int = 0,  // 어느 게시물에 단 댓글인지
    var commentIdx : Int = 0,  // 답글 구분을 위한 댓글 인덱스
    var comment : String = "",  // 댓글 내용
    var date : String = "",  // 댓글 단 시간
    var like : Int = 0  // 좋아요 달린 개수
)
