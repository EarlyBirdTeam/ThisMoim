package com.websocket.board.controller;

import com.websocket.board.config.JwtTokenProvider;
import com.websocket.board.model.Board;
import com.websocket.board.repo.ChannelRepository;
import com.websocket.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Slf4j
@RequiredArgsConstructor
@Controller
@CrossOrigin(origins = "http://localhost:8081")
public class BoardController {

    private final JwtTokenProvider jwtTokenProvider;
    private final ChannelRepository channelRepository;
    private final BoardService boardService;

    /**
     * websocket "/pub/board/message"로 들어오는 메시징을 처리한다.
     */
    @MessageMapping("/board/message")
    public void message(Board message, @Header("token") String token) {
        String nickname = jwtTokenProvider.getUserNameFromJwt(token);
        // 로그인 회원 정보로 대화명 설정
//        message.setSender(nickname);
        // 채널 인원수 세팅
        message.setUserCount(channelRepository.getUserCount(message.getChannelId()));
        // Websocket에 발행된 메시지를 redis로 발행(publish)
        boardService.syncBoardStatus(message);
    }
}
