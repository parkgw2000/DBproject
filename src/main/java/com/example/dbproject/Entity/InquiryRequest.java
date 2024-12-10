package com.example.dbproject.Entity;

import lombok.Data;

@Data
public class InquiryRequest {
    private String title;
    private String content;
    private Long userNum;
}
