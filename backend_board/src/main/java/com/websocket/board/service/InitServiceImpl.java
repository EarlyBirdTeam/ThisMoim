package com.websocket.board.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.websocket.board.dto.Board;
import com.websocket.board.repo.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
public class InitServiceImpl implements InitService {

    @Autowired
    BoardRepository boardRepository;
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public Board getBoard(String channelId) throws JsonProcessingException {
        ValueOperations vop = redisTemplate.opsForValue();
        Board board = (Board) vop.get(channelId);
        if (board == null) {
            board = boardRepository.findByChannelId(channelId);
            vop.set(channelId, board);
        }
        System.out.println(board);
        return board;
    }
}
