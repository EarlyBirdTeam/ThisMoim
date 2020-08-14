package com.websocket.board.model.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse implements Serializable {

    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private Long expiryDuration;

}
