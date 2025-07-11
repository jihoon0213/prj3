package com.example.backend.member.dto;

import lombok.Data;

@Data
public class ChangePasswordForm {
    private String email;
    private String oldPassword;
    private String newPassword;
}