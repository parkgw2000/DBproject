package com.example.dbproject.Controller;

import com.example.dbproject.Entity.Inquiry;
import com.example.dbproject.Entity.InquiryRequest;
import com.example.dbproject.Service.InquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inquiry")
@RequiredArgsConstructor
public class InquiryController {
    private final InquiryService inquiryService;

    // 문의글 작성
    @PostMapping
    public ResponseEntity<Inquiry> createInquiry(@RequestBody InquiryRequest inquiryRequest) {
        Inquiry savedInquiry = inquiryService.createInquiry(
                inquiryRequest.getTitle(),
                inquiryRequest.getContent(),
                inquiryRequest.getUserNum()
        );
        return ResponseEntity.ok(savedInquiry);
    }

    // 전체 문의글 목록 조회
    @GetMapping("/list")
    public ResponseEntity<List<Inquiry>> getAllInquiries() {
        List<Inquiry> inquiries = inquiryService.getAllInquiries();
        return ResponseEntity.ok(inquiries);
    }

    // 특정 사용자의 문의글 목록 조회
    @GetMapping("/user/{userNum}")
    public ResponseEntity<List<Inquiry>> getUserInquiries(@PathVariable Long userNum) {
        List<Inquiry> inquiries = inquiryService.getUserInquiries(userNum);
        return ResponseEntity.ok(inquiries);
    }

    // 문의글 상세 조회
    @GetMapping("/{inquiryNum}")
    public ResponseEntity<Inquiry> getInquiry(@PathVariable Long inquiryNum) {
        Inquiry inquiry = inquiryService.getInquiry(inquiryNum);
        return ResponseEntity.ok(inquiry);
    }

    // 답변 등록/수정
    @PutMapping("/{inquiryNum}/reply")
    public ResponseEntity<Inquiry> updateReply(
            @PathVariable Long inquiryNum,
            @RequestParam String reply
    ) {
        Inquiry updatedInquiry = inquiryService.updateReply(inquiryNum, reply);
        return ResponseEntity.ok(updatedInquiry);
    }
}