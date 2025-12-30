package com.zht.dto;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private String username;
    private String nickname;
}

