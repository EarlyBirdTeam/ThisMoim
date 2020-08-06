package com.websocket.board.controller;

import com.websocket.board.config.JwtTokenProvider;
import com.websocket.board.dto.Channel;
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

@RequiredArgsConstructor
@Slf4j
@Controller
public class BoardController {

    //private final JwtTokenProvider jwtTokenProvider;
    private final ChannelRedisRepository channelRedisRepository;
    private final BoardService boardService;
    private final DBSyncService dbSyncService;

    /**
     * websocket "/pub/board/message"로 들어오는 메시징을 처리한다.
     */
    @MessageMapping("/board/message")
    public void message(SocketBoardMessage message, @Header("token") String token) {
        //String nickname = jwtTokenProvider.getUserNameFromJwt(token);
        // 로그인 회원 정보로 대화명 설정
        //message.setSender(nickname);
        // 채널 인원수 세팅
        message.setUserCount(channelRedisRepository.getUserCount(message.getChannelId()));

        // 채널 포스트잇 카운트 세팅
        // 채널 포스트잇 카운트가 레디스에 저장된 idCount와 다르면? DB 에 업데이트 & 레디스도 업데이트
        if(message.getIdCount() != channelRedisRepository.findChannelById(message.getChannelId()).getIdCount()) {
            dbSyncService.channelDBIdCountSync(message.getChannelId(), message.getIdCount());

            Channel channel = channelRedisRepository.findChannelById(message.getChannelId());
            channel.setIdCount(message.getIdCount());
            channelRedisRepository.updateChannel(channel);
        }

        // Websocket에 발행된 메시지(클라이언트로 부터 받은 메시지)를 redis로 발행(publish)
        boardService.syncSocketBoardStatus(message);

        dbSyncService.postitDBSync(message);
    }
}
