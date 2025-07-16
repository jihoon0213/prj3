package com.example.backend.board.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "board_file", schema = "prj3")
public class BoardFile {
    @EmbeddedId
    private BoardFileId id;

    @MapsId("boardId")
    @ManyToOne(optional = false)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

}