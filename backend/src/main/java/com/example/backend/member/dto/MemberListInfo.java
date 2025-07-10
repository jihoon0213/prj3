package com.example.backend.member.dto;

import java.time.LocalDateTime;

public interface MemberListInfo {
    String getEmail();

    String getNickName();

    LocalDateTime getInsertedAt();
}
