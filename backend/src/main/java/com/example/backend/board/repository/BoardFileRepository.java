package com.example.backend.board.repository;

import com.example.backend.board.entity.BoardFile;
import com.example.backend.board.entity.BoardFileId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardFileRepository extends JpaRepository<BoardFile, BoardFileId> {
    List<BoardFile> findByBoardId(Integer id);
}