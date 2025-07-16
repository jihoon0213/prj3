package com.example.backend.like.controller;

import com.example.backend.like.dto.BoardLikeDto;
import com.example.backend.like.dto.LikeForm;
import com.example.backend.like.entity.BoardLike;
import com.example.backend.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/like")
public class LikeController {
    private final LikeService likeService;

    @GetMapping("board/{boardId}")
    public BoardLikeDto get(
            @PathVariable("boardId") Integer boardId,
            Authentication authentication) {

        return likeService.get(boardId, authentication);

    }

    @PutMapping
    public void like(@RequestBody LikeForm likeForm, Authentication authentication) {
        likeService.update(likeForm, authentication);
    }
}