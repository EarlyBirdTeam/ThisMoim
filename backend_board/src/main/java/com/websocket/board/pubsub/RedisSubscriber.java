package com.websocket.board.pubsub;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.websocket.board.model.Board;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisSubscriber {

    private final ObjectMapper objectMapper;
    private final SimpMessageSendingOperations messagingTemplate;

    /**
     * Redis에서 메시지가 발행(publish)되면 대기하고 있던 Redis Subscriber가 해당 메시지를 받아 처리한다.
     */
    public void sendMessage(String publishMessage) {
        try {
            // Board 객채로 맵핑
            Board board = objectMapper.readValue(publishMessage, Board.class);
            // 채널을 구독한 클라이언트에게 메시지 발송
            messagingTemplate.convertAndSend("/sub/board/channel/" + board.getChannelId(), board);
            //log.debug(board.toString());
        } catch (Exception e) {
            log.error("Exception {}", e);
        }
    }
}
