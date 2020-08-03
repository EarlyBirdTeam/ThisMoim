package com.websocket.board.controller;

import com.websocket.board.config.JwtTokenProvider;
import com.websocket.board.model.Channel;
import com.websocket.board.repo.ChannelRepository;
import com.websocket.board.model.LoginInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
@CrossOrigin(origins = "http://localhost:8081")
public class ChannelController {

    private final ChannelRepository channelRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/channel")
    public String channels() {
        return "/board/channel";
    }

    @GetMapping("/channels")
    @ResponseBody
    public List<Channel> channel() {
        List<Channel> channels = channelRepository.findAllChannel();
        channels.stream().forEach(room -> room.setUserCount(channelRepository.getUserCount(room.getChannelId())));
        return channels;
    }

    @PostMapping("/channel")
    @ResponseBody
    public Channel createChannel(@RequestParam(value="channelName", required= false) String channelName) {
        return channelRepository.createChannel(channelName);
    }

    @GetMapping("/channel/enter/{channelId}")
    public String channelDetail(Model model, @PathVariable String channelId) {
        model.addAttribute("channelId", channelId);
        return "/board/channeldetail";
    }

    @GetMapping("/channel/{channelId}")
    @ResponseBody
    public Channel channelInfo(@PathVariable String channelId) {
        return channelRepository.findChannelById(channelId);
    }

    @GetMapping("/user")
    @ResponseBody
    public LoginInfo getUserInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        return LoginInfo.builder().name(name).token(jwtTokenProvider.generateToken(name)).build();
    }
}

