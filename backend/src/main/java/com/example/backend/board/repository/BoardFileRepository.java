package com.example.backend.board.repository;

import com.example.backend.board.entity.BoardFile;
import com.example.backend.board.entity.BoardFileId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardFileRepository extends JpaRepository<BoardFile, BoardFileId> {
}