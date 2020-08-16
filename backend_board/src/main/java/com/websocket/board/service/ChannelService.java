package com.websocket.board.service;

import com.websocket.board.model.Channel;
import com.websocket.board.payload.CreateChannelRequest;

import java.util.List;

public interface ChannelService {
    Channel saveChannel(CreateChannelRequest createChannelRequest, String channelId);
    List<Channel> getMyChannels(String token);
}
