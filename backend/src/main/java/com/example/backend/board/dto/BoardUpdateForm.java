package com.example.backend.board.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class BoardUpdateForm {
    private Integer id;
    private String title;
    private String content;

    private List<MultipartFile> files;
    private String[] deleteFiles;
}