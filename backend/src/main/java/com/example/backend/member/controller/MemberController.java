package com.example.backend.member.controller;

import com.example.backend.member.dto.MemberForm;
import com.example.backend.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("add")
    public ResponseEntity<?> add(@RequestBody MemberForm memberForm) {
//        System.out.println("memberForm = " + memberForm);
        try {
            memberService.add(memberForm);
        } catch (Exception e) {
            e.printStackTrace();
            String message = e.getMessage();
            return ResponseEntity.badRequest().body(
                    Map.of("message",
                            Map.of("type", "error",
                                    "text", message)));
        }

        return ResponseEntity.ok().body(
                Map.of("message",
                        Map.of("type", "success",
                                "text", "회원 가입 되었습니다."))
        );
    }

}