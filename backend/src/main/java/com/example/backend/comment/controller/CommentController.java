package com.example.backend.comment.controller;

import com.example.backend.comment.dto.CommentForm;
import com.example.backend.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> addComment(@RequestBody CommentForm comment,
                                        Authentication authentication) {
        try {
            commentService.add(comment, authentication);
            return ResponseEntity.ok()
                    .body(Map.of("message",
                            Map.of("type", "success",
                                    "text", "새 댓글이 등록되었습니다.")));
        } catch (Exception e) {
            return ResponseEntity.ok()
                    .body(Map.of("message",
                            Map.of("type", "error",
                                    "text", e.getMessage())));
        }

    }
}