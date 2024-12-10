package com.example.dbproject.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CookStepResponse {
    private Long cookNum;
    private Integer index;
    private String details;
    private byte[] filename;

    public static CookStepResponse from(Cook cook) {
        return CookStepResponse.builder()
                .cookNum(cook.getCookNum())
                .index(cook.getIndex())
                .details(cook.getDetails())
                .filename(cook.getFilename())
                .build();
    }
}
