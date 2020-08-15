package com.websocket.board.service;

import com.websocket.board.model.Channel;

import java.util.List;

public interface ChannelService {
    Channel saveChannel(String token, String channelName, String channelId);
    List<Channel> getMyChannels(String token);
}
