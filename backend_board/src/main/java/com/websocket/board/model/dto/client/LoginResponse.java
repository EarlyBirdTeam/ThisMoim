package com.websocket.board.model.dto.client;

import lombok.*;
import org.apache.catalina.connector.Response;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse implements Serializable {

    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private Long expiryDuration;
    private UserInfoResponse userInfoResponse;

}
