package com.example.backend.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardDto {
    private Integer id;
    private String title;
    private String content;
    private String authorEmail;
    private String authorNickName;
    private LocalDateTime insertedAt;

    private List<BoardFileDto> files;

    public BoardDto(Integer id,
                    String title,
                    String content,
                    String authorEmail,
                    String authorNickName,
                    LocalDateTime insertedAt) {

        this.id = id;
        this.title = title;
        this.content = content;
        this.authorEmail = authorEmail;
        this.authorNickName = authorNickName;
        this.insertedAt = insertedAt;
    }
}