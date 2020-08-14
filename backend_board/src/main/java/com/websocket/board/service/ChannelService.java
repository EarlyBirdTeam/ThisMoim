package com.websocket.board.service;

import com.websocket.board.model.Channel;

public interface ChannelService {
    Channel saveChannel(String token, String channelName, String channelId);
}
