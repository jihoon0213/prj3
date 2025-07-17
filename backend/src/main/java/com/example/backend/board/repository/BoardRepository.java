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
                        b.insertedAt,
                        COUNT(DISTINCT c),
                        COUNT(DISTINCT l),
                        COUNT(DISTINCT f))
            FROM Board b JOIN Member m
                                ON b.author.email = m.email
                        LEFT JOIN Comment c
                                ON b.id = c.board.id
                        LEFT JOIN BoardLike l
                                ON b.id = l.board.id
                        LEFT JOIN BoardFile f
                                ON b.id = f.board.id
            WHERE b.title LIKE %:keyword%
               OR b.content LIKE %:keyword%
               OR m.nickName LIKE %:keyword%
            GROUP BY b.id
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

    @Query("""
            SELECT b.id
            FROM Board b
            WHERE b.author = :author
            """)
    List<Integer> listBoardIdByAuthor(Member author);
}