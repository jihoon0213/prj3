package com.example.backend.member.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "auth", schema = "prj3")
public class Auth {
    @EmbeddedId
    private AuthId id;

    @MapsId("memberEmail")
    @ManyToOne(optional = false)
    @JoinColumn(name = "member_email", nullable = false)
    private Member member;

}