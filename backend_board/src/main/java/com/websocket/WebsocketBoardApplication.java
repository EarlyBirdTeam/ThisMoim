package com.websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"com.websocket.auth","com.websocket.board"})
@EntityScan("com.websocket.board.model")
@EnableJpaRepositories({"com.websocket.board.repo","com.websocket.auth.repo"})
public class WebsocketBoardApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebsocketBoardApplication.class, args);
    }

}
