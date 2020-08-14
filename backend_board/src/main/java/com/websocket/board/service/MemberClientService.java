package com.websocket.board.service;

import com.websocket.board.model.client.LoginRequest;
import com.websocket.board.model.client.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberClientService {

    private final ApiService<LoginResponse> apiService;

    public LoginResponse callPostExternalServer(LoginRequest request) {
        return apiService.post("http://i3a510.p.ssafy.io:9004/api/auth/login", HttpHeaders.EMPTY, request, LoginResponse.class).getBody();
    }

}
