package com.example.backend.board.repository;

import com.example.backend.board.entity.Board;
import com.example.backend.board.entity.BoardFile;
import com.example.backend.board.entity.BoardFileId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardFileRepository extends JpaRepository<BoardFile, BoardFileId> {
    List<BoardFile> findByBoardId(Integer id);


    void deleteByBoard(Board board);

    @Query("""
            SELECT f.id.name
            FROM BoardFile f
            WHERE f.board = :board
            """)
    List<String> listFileNameByBoard(Board board);
}