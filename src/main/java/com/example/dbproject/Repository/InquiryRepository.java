package com.example.dbproject.Repository;

import com.example.dbproject.Entity.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InquiryRepository extends JpaRepository<Inquiry, Long> {

    // 특정 사용자의 문의글 목록 조회
    List<Inquiry> findByUser_UsernumOrderByCreatedateDesc(Long user_num);

    // 전체 문의글 목록 (최신순)
    List<Inquiry> findAllByOrderByCreatedateDesc();

    // 답변이 달린/안 달린 문의글 조회 (reply의 null 여부로 판단)
    List<Inquiry> findByReplyIsNotNullOrderByCreatedateDesc();
    List<Inquiry> findByReplyIsNullOrderByCreatedateDesc();
}

