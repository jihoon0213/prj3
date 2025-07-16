package com.example.backend.like.dto;

import lombok.Data;

@Data
public class BoardLikeDto {
    private Long count;
    private Boolean liked;
}