package com.websocket.board.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.websocket.board.model.Channel;
import com.websocket.board.model.SocketBoardMessage;
import com.websocket.board.model.user.User;
import com.websocket.board.model.user.UserChannel;
import com.websocket.board.repo.ChannelRedisRepository;
import com.websocket.board.repo.ChannelRepository;
import com.websocket.board.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class BoardInitController {

    //@Autowired
    //InitService initService;
    private final ChannelRedisRepository channelRedisRepository;
    private final ChannelService channelService;

    @CrossOrigin("*")
    @GetMapping("/board/{channelId}")
    public SocketBoardMessage getBoardStatusInit(@PathVariable("channelId") String channelId)
            throws JsonProcessingException {
        //return initService.getBoard(channelId);
        // 이 부분 레디스에 정보가 없다면 디비에서 가져오도록 수정할 예정
        SocketBoardMessage socketBoardMessage = channelRedisRepository.findBoardByChannelId(channelId);
        socketBoardMessage.setMemberList(channelService.getChannelMember(channelId));
//        if(socketBoardMessage == null) {
//            initService.getBoard(channelId);
//        }
        return socketBoardMessage;
    }

}
