package com.websocket.board.service;

import com.websocket.board.model.Channel;
import com.websocket.board.payload.CreateChannelRequest;
import com.websocket.board.payload.InviteChannelRequest;

import java.util.List;

public interface ChannelService {
    Channel saveChannel(CreateChannelRequest createChannelRequest, String channelId);
    Boolean saveInvitedChannel(InviteChannelRequest inviteChannelRequest, String channelId);
    List<Channel> getMyChannels(String token);
    List<String> getChannelMember(String channelId);
}
