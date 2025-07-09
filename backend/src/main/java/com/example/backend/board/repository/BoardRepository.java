package com.example.backend.board.repository;

import com.example.backend.board.dto.BoardListInfo;
import com.example.backend.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Integer> {
    List<BoardListInfo> findAllByOrderByIdDesc();
}