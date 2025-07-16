package com.example.backend.like.entity;

import com.example.backend.board.entity.Board;
import com.example.backend.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "board_like", schema = "prj3")
public class BoardLike {
    @EmbeddedId
    private BoardLikeId id;

    @MapsId("boardId")
    @ManyToOne(optional = false)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @MapsId("memberEmail")
    @ManyToOne(optional = false)
    @JoinColumn(name = "member_email", nullable = false)
    private Member member;

}