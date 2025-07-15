package com.example.backend.comment.dto;

import lombok.Data;

@Data
public class CommentForm {
    private Integer id;
    private Integer boardId;
    private String comment;
}