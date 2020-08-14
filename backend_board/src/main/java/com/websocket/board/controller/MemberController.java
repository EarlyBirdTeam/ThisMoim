package com.websocket.board.controller;

import com.websocket.board.model.auth.AuthResponse;
import com.websocket.board.model.client.LoginRequest;
import com.websocket.board.model.client.LoginResponse;
import com.websocket.board.service.MemberClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class MemberController {

    private final MemberClientService memberClientService;

    @PostMapping("/auth/login")
    @ResponseBody
    public AuthResponse createChannel(@RequestBody LoginRequest request) {
        LoginResponse loginResponse = memberClientService.callPostExternalServer(request);
        AuthResponse authResponse = AuthResponse.builder()
                .accessToken(loginResponse.getAccessToken())
                .refreshToken(loginResponse.getRefreshToken())
                .tokenType(loginResponse.getTokenType())
                .expiryDuration(loginResponse.getExpiryDuration())
                .build();
        return authResponse;
    }
}

