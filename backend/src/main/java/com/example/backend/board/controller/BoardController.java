package com.example.backend.board.controller;

import com.example.backend.board.dto.BoardAddForm;
import com.example.backend.board.dto.BoardDto;
import com.example.backend.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

//@Controller
//@ResponseBody
@RestController // Controller + ResponseBody
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PutMapping("{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> updateBoard(@PathVariable Integer id,
                                         @RequestBody BoardDto boardDto,
                                         Authentication authentication) {
        boolean result = boardService.validate(boardDto);
        if (result) {
            boardService.update(boardDto, authentication);

            return ResponseEntity.ok().body(Map.of("message",
                    Map.of("type", "success", "text", id + "번 게시물이 수정 되었습니다.")));
        } else {
            return ResponseEntity.badRequest().body(Map.of("message",
                    Map.of("type", "error", "text", "입력한 내용이 유효하지 않습니다.")));
        }

    }

    @DeleteMapping("{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> deleteBoard(@PathVariable Integer id,
                                         Authentication authentication) {
        boardService.deleteById(id, authentication);
        return ResponseEntity.ok().body(Map.of("message",
                Map.of("type", "success", "text", id + "번 게시물이 삭제 되었습니다.")));
    }

    @GetMapping("{id}")
    public BoardDto getBoardById(@PathVariable Integer id) {
        return boardService.getBoardById(id);
    }

    @GetMapping("list")
    public Map<String, Object> getAllBoards(
            @RequestParam(value = "q", defaultValue = "") String keyword,
            @RequestParam(value = "p", defaultValue = "1") Integer pageNumber) {

        return boardService.list(keyword, pageNumber);
    }

    @PostMapping("add")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> add(BoardAddForm dto,
                                 Authentication authentication) {
        // 값들이 유효한지 확인
        boolean result = boardService.validateForAdd(dto);

        if (result) {
            // service에게 넘겨서 일 시키기
            boardService.add(dto, authentication);

            return ResponseEntity.ok().body(Map.of(
                    "message", Map.of(
                            "type", "success",
                            "text", "새 글이 저장되었습니다.")));

        } else {
            return ResponseEntity.badRequest().body(Map.of(
                    "message", Map.of(
                            "type", "error",
                            "text", "입력한 내용이 유효하지 않습니다.")));
        }
    }
}