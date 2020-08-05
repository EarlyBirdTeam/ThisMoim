package com.websocket.board.controller;

import com.websocket.board.config.JwtTokenProvider;
import com.websocket.board.model.SocketBoardMessage;
import com.websocket.board.repo.ChannelRedisRepository;
import com.websocket.board.service.BoardService;
import com.websocket.board.service.DBSyncService;
import com.websocket.board.service.InitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Slf4j
@RequiredArgsConstructor
@Controller
public class BoardController {

    private final JwtTokenProvider jwtTokenProvider;
    private final ChannelRedisRepository channelRedisRepository;
    private final BoardService boardService;
    @Autowired
    InitService initService;
    @Autowired
    DBSyncService dbSyncService;


    /**
     * websocket "/pub/board/message"로 들어오는 메시징을 처리한다.
     */
    @MessageMapping("/board/message")
    public void message(SocketBoardMessage message, @Header("token") String token) {
        String nickname = jwtTokenProvider.getUserNameFromJwt(token);
        // 로그인 회원 정보로 대화명 설정
        //message.setSender(nickname);
        // 채널 인원수 세팅
        message.setUserCount(channelRedisRepository.getUserCount(message.getChannelId()));

        // Websocket에 발행된 메시지(클라이언트로 부터 받은 메시지)를 redis로 발행(publish)
        boardService.syncSocketBoardStatus(message);
        // mariadb 에 sync

//        Board board = new Board()
//                .builder()
//                .channelId(message.getChannelId())
//                .postitList(message.getPostitList())
//                .build();
        dbSyncService.postitDBSync(message);

    }
}
