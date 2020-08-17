package com.websocket.board.controller;

import com.websocket.board.config.JwtTokenProvider;
import com.websocket.board.model.Channel;
import com.websocket.board.model.LoginInfo;
import com.websocket.board.payload.*;
import com.websocket.board.repo.ChannelRedisRepository;
import com.websocket.board.service.BoardClientService;
import com.websocket.board.service.ChannelService;
import com.websocket.board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/board")
public class ChannelController {

    private final ChannelRedisRepository channelRedisRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final ChannelService channelService;
    private final BoardClientService boardClientService;

//    @GetMapping("/channels")
//    @ResponseBody
//    public List<Channel> channel() {
//        List<Channel> channels = channelRedisRepository.findAllChannel();
//        channels.stream().forEach(channel -> channel.setUserCount(channelRedisRepository.getUserCount(channel.getChannelId())));
//        return channels;
//    }
    @PostMapping("/channels")
    @ResponseBody
    public List<Channel> myChannel(
            @RequestHeader(name = "Authorization") String Authorization,
            @RequestBody UserInfoRequest userInfoRequest) {
        // 각 사용자가 가진 채널 리스트 전달하기 <- 디비에서 가져오기
        List<Channel> channels = channelService.getMyChannels(userInfoRequest.getEmail());
        //List<Channel> channels = channelRedisRepository.findAllChannel();
        // 현재 채널에 접속해 있는 인원을 가져오는 부분
        //channels.stream().forEach(channel -> channel.setUserCount(channelRedisRepository.getUserCount(channel.getChannelId())));
        return channels;
    }

    @PostMapping("/channel")
    @ResponseBody
    public Channel createChannel(
            @RequestHeader(name = "Authorization") String Authorization,
            @RequestBody CreateChannelRequest createChannelRequest) {
        String channelId = UUID.randomUUID().toString();
        // save channel in redis
        Channel channel = channelRedisRepository.createChannel(createChannelRequest.getChannelName(), channelId);
        // save channel in mariadb
        // check token validation before creating channel
        // 인증 서버에서 토큰 검증 api 완성되면 사용할 것
//        ValidTokenRequest validTokenRequest = new ValidTokenRequest()
//                .builder()
//                .token(Authorization)
//                .build();
//        if(boardClientService.checkToken(validTokenRequest).getIsValid()) {
//            channelService.saveChannel(createChannelRequest, channelId);
//        }
        // 인증 서버에서 토큰 검증 api 완성되기 전 테스트
        channelService.saveChannel(createChannelRequest, channelId);
        return channel;
    }

    @PostMapping("/channel/invitation")
    @ResponseBody
    public InviteChannelResponse enterInvitedChannel(@RequestBody InviteChannelRequest inviteChannelRequest) {
        String channelId = inviteChannelRequest.getChannelId();
        // save channel in mariadb
        if(channelService.saveInvitedChannel(inviteChannelRequest, channelId)) {
            return new InviteChannelResponse().builder().message("Success Invitation").success(true).build();
        } else {
            return new InviteChannelResponse().builder().message("Fail Invitation").success(false).build();
        }
    }

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
        return LoginInfo.builder()
                .name(name)
                .token(jwtTokenProvider.generateToken(name))
                .build();
    }
}