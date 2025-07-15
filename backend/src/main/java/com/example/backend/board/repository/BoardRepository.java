package com.example.backend.board.repository;

import com.example.backend.board.dto.BoardDto;
import com.example.backend.board.dto.BoardListDto;
import com.example.backend.board.dto.BoardListInfo;
import com.example.backend.board.entity.Board;
import com.example.backend.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Integer> {
    List<BoardListInfo> findAllByOrderByIdDesc();

    @Query(value = """
            SELECT new com.example.backend.board.dto.BoardListDto(
                        b.id,
                        b.title,
                        m.nickName,
                        b.insertedAt)
            FROM Board b JOIN Member m
                        ON b.author.email = m.email
            WHERE b.title LIKE %:keyword%
               OR b.content LIKE %:keyword%
               OR m.nickName LIKE %:keyword%
            ORDER BY b.id DESC
            """)
    Page<BoardListDto> findAllBy(String keyword, PageRequest pageRequest);

    @Query(value = """
            SELECT 
                new com.example.backend.board.dto.BoardDto
                   (b.id,
                   b.title,
                   b.content,
                   m.email,
                   m.nickName,
                   b.insertedAt)
            FROM Board b JOIN Member m
                ON b.author.email = m.email
            WHERE b.id = :id
            """)
    BoardDto findBoardById(Integer id);

    void deleteByAuthor(Member author);

    List<Board> findByAuthor(Member db);
}