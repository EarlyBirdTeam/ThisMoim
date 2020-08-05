package com.websocket.board.service;

import com.websocket.board.model.Board;
import com.websocket.board.model.SocketBoardMessage;
import com.websocket.board.repo.PostitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
public class InitServiceImpl implements InitService {

    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    PostitRepository postitRepository;

    @Override
    public Board getBoard(String channelId) {
        // 현재는 레디스에서 간헐적으로 DB에 저장되는 방식이 아니므로 레디스에서 받아오는 것은 보류
        ValueOperations vop = redisTemplate.opsForValue();
//        SocketBoardMessage board = (SocketBoardMessage) vop.get(channelId);
//        if (board == null) {
//            Channel channel = channelRepository.findByChannelId(channelId);
//            vop.set(channelId, board);
//        }
        Board board = new Board()
                .builder()
                .postitList(postitRepository.findAllByChannel(channelId))
                .build();
        System.out.println(board);
        return board;
    }
}
