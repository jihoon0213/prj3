package com.example.backend.board.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@ToString
@Embeddable
public class BoardFileId implements Serializable {
    private static final long serialVersionUID = 8839600998482464894L;
    @Column(name = "board_id", nullable = false)
    private Integer boardId;

    @Column(name = "name", nullable = false, length = 300)
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BoardFileId entity = (BoardFileId) o;
        return Objects.equals(this.name, entity.name) &&
               Objects.equals(this.boardId, entity.boardId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, boardId);
    }

}