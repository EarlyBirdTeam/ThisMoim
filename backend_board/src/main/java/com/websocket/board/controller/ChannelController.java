package com.websocket.board.controller;

import com.websocket.board.config.JwtTokenProvider;
import com.websocket.board.dto.Channel;
import com.websocket.board.repo.ChannelRedisRepository;
import com.websocket.board.model.LoginInfo;
import com.websocket.board.repo.ChannelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
@CrossOrigin(origins = "http://localhost:8081")
public class ChannelController {

    private final ChannelRedisRepository channelRedisRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    ChannelRepository channelRepository;

    @GetMapping("/channel")
    public String channels() {
        return "/board/channel";
    }

    @GetMapping("/channels")
    @ResponseBody
    public List<Channel> channel() {
        List<Channel> channels = channelRedisRepository.findAllChannel();
        channels.stream().forEach(room -> room.setIdCount(channelRedisRepository.getUserCount(room.getChannelId())));
        return channels;
    }

    @PostMapping("/channel")
    @ResponseBody
    public Channel createChannel(@RequestParam("channelName") String channelName) {
        System.out.println(channelName);
        // save channel in mariadb
        Channel channel = new Channel().builder()
                .channelName(channelName)
                .build();
        Channel savedChannel = channelRepository.save(channel);
        // save channel in redis
        channelRedisRepository.createChannel(savedChannel);

        return savedChannel;
    }

//    @GetMapping("/channel/enter/{channelId}")
//    public BoardResponse channelDetail(@PathVariable String channelId) {
//        model.addAttribute("channelId", channelId);
//        return "/board/channeldetail";
//    }

    @GetMapping("/channel/{channelId}")
    @ResponseBody
    public Channel channelInfo(@PathVariable String channelId) {
        return channelRedisRepository.findChannelById(channelId);
    }

    @GetMapping("/user")
    @ResponseBody
    public LoginInfo getUserInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        return LoginInfo.builder().name(name).token(jwtTokenProvider.generateToken(name)).build();
    }
}

