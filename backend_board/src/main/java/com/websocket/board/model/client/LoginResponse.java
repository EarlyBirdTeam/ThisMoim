package com.websocket.board.model.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.connector.Response;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse extends Response implements Serializable {

    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private Long expiryDuration;
    private User user;

}
