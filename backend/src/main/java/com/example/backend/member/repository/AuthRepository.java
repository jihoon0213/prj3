package com.example.backend.member.repository;

import com.example.backend.member.entity.Auth;
import com.example.backend.member.entity.AuthId;
import com.example.backend.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthRepository extends JpaRepository<Auth, AuthId> {
    List<Auth> findByMember(Member member);
}