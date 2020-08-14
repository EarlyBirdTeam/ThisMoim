package com.websocket.board.controller;

import com.websocket.board.model.dto.client.LoginRequest;
import com.websocket.board.model.dto.client.LoginResponse;
import com.websocket.board.model.dto.client.UserInfoResponse;
import com.websocket.board.service.MemberClientService;
import com.websocket.board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {

    private final MemberClientService memberClientService;
    private final UserService userService;

    @PostMapping("/auth/login")
    @ResponseBody
    public LoginResponse createChannel(@RequestBody LoginRequest request) {
        // auth 서버로 로그인 요청 후 토큰과 사용자 정보 받기
        LoginResponse loginResponse = memberClientService.callPostExternalServer(request);
        UserInfoResponse userInfoResponse = memberClientService.callPostUserInfoExternalServer(
                loginResponse.getAccessToken(),
                request.getEmail());

        // 보드 서버에 로그인한 사용자 정보 저장
        // userService.saveUser(loginResponse);
        userService.saveUserTest(loginResponse, userInfoResponse);

        return loginResponse;
    }
}

