package com.websocket.board.service;

import com.websocket.board.model.Board;
import com.websocket.board.repo.ChannelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final ChannelTopic channelTopic;
    private final RedisTemplate redisTemplate;
    private final ChannelRepository channelRepository;

    /**
     * destination정보에서 channelId 추출
     */
    public String getChannelId(String destination) {
        int lastIndex = destination.lastIndexOf('/');
        if (lastIndex != -1)
            return destination.substring(lastIndex + 1);
        else
            return "";
    }

    /**
     * 보드 상태 동기화
     */
    public void syncBoardStatus(Board board) {
        board.setUserCount(channelRepository.getUserCount(board.getChannelId()));
//        if (ChatMessage.MessageType.ENTER.equals(chatMessage.getType())) {
//            chatMessage.setMessage(chatMessage.getSender() + "님이 방에 입장했습니다.");
//            chatMessage.setSender("[알림]");
//        } else if (ChatMessage.MessageType.QUIT.equals(chatMessage.getType())) {
//            chatMessage.setMessage(chatMessage.getSender() + "님이 방에서 나갔습니다.");
//            chatMessage.setSender("[알림]");
//        }
        redisTemplate.convertAndSend(channelTopic.getTopic(), board);
    }

}
