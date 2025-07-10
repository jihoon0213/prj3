package com.example.backend.member.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemberDto {
    private String email;
    private String nickName;
    private String info;
    private LocalDateTime insertedAt;


}
