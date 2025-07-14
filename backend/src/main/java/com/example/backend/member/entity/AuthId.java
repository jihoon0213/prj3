package com.example.backend.member.entity;

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
public class AuthId implements Serializable {
    private static final long serialVersionUID = 5882053869900242433L;
    @Column(name = "member_email", nullable = false)
    private String memberEmail;

    @Column(name = "auth_name", nullable = false)
    private String authName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AuthId entity = (AuthId) o;
        return Objects.equals(this.authName, entity.authName) &&
               Objects.equals(this.memberEmail, entity.memberEmail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authName, memberEmail);
    }

}