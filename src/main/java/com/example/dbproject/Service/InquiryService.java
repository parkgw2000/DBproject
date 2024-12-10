package com.example.dbproject.Service;

import com.example.dbproject.Entity.Inquiry;
import com.example.dbproject.Entity.User;
import com.example.dbproject.Repository.InquiryRepository;
import com.example.dbproject.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class InquiryService {
    private final InquiryRepository inquiryRepository;
    private final UserRepository userRepository;

    // 문의글 작성
    public Inquiry createInquiry(String title, String content, Long userNum) {
        try {
            User user = userRepository.findById(userNum)
                    .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 사용자 ID입니다: " + userNum));
            Inquiry inquiry = new Inquiry();
            inquiry.setTitle(title);
            inquiry.setContent(content);
            inquiry.setUser(userRepository.findById(userNum).orElseThrow(() -> new IllegalArgumentException("Invalid user ID")));
            inquiry.setReply(""); // reply를 빈 문자열로 설정
            inquiry.setCreatedate(LocalDateTime.now());
            return inquiryRepository.save(inquiry);
        } catch (Exception e) {
            // 로그 출력
            System.err.println("문의 등록 중 오류 발생: " + e.getMessage());
            throw e;
        }
    }

    // 문의글 목록 조회
    public List<Inquiry> getAllInquiries() {
        return inquiryRepository.findAllByOrderByCreatedateDesc();
    }

    // 특정 사용자의 문의글 조회
    public List<Inquiry> getUserInquiries(Long userNum) {
        return inquiryRepository.findByUser_UsernumOrderByCreatedateDesc(userNum);
    }

    // 문의글 상세 조회
    public Inquiry getInquiry(Long inquiryNum) {
        return inquiryRepository.findById(inquiryNum)
                .orElseThrow(() -> new RuntimeException("Inquiry not found"));
    }

    // 답변 등록/수정
    public Inquiry updateReply(Long inquiryNum, String reply) {
        Inquiry inquiry = inquiryRepository.findById(inquiryNum)
                .orElseThrow(() -> new RuntimeException("Inquiry not found"));

        inquiry.setReply(reply);
        return inquiryRepository.save(inquiry);
    }
}