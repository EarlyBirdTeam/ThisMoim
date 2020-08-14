package com.websocket.board.service;

import com.websocket.board.model.dto.client.LoginRequest;
import com.websocket.board.model.dto.client.LoginResponse;
import com.websocket.board.model.dto.client.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberClientService {

    private final ApiService<LoginResponse> apiService;
    private final ApiService<UserInfoResponse> userInfoResponseApiService;

    public LoginResponse callPostExternalServer(LoginRequest request) {
        return apiService.post("http://i3a510.p.ssafy.io:9004/api/auth/login", HttpHeaders.EMPTY, request, LoginResponse.class).getBody();
    }

    public UserInfoResponse callPostUserInfoExternalServer(String token, String email) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        //HttpEntity requestEntity =  new  HttpEntity(request, headers);

        return userInfoResponseApiService.get("http://i3a510.p.ssafy.io:9004/api/user/userInfo?email=" + email, headers, UserInfoResponse.class).getBody();
    }

}
