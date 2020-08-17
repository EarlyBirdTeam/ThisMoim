package com.websocket.board.service;

import com.websocket.board.model.Channel;
import com.websocket.board.model.user.User;
import com.websocket.board.model.user.UserChannel;
import com.websocket.board.payload.CreateChannelRequest;
import com.websocket.board.payload.InviteChannelRequest;
import com.websocket.board.repo.ChannelRepository;
import com.websocket.board.repo.UserChannelRepository;
import com.websocket.board.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ChannelServiceImpl implements ChannelService {

    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;
    private final UserChannelRepository userChannelRepository;

    @Override
    public Channel saveChannel(CreateChannelRequest createChannelRequest, String channelId) {

        Channel channel = new Channel().builder()
                .channelId(channelId)
                .channelName(createChannelRequest.getChannelName())
                .build();

        Channel channelResponse = channelRepository.save(channel);

        Optional<User> user = userRepository.findByEmail(createChannelRequest.getEmail());

        if(user.isPresent()) {
            UserChannel userChannel = new UserChannel().builder()
                    .user(user.get())
                    .channel(channelResponse)
                    .build();

            userChannelRepository.save(userChannel);
        }

        return channelResponse;
    }

    @Override
    public Boolean saveInvitedChannel(InviteChannelRequest inviteChannelRequest, String channelId) {

        Optional<Channel> channelResponse = channelRepository.findByChannelId(channelId);

        Optional<User> user = userRepository.findByEmail(inviteChannelRequest.getUser().getEmail());

        Boolean isSaved = false;
        if(user.isPresent()) {
            UserChannel userChannel = new UserChannel().builder()
                    .user(user.get())
                    .channel(channelResponse.get())
                    .build();

            userChannelRepository.save(userChannel);
            isSaved = true;
        }

        return isSaved;
    }

    @Override
    public List<Channel> getMyChannels(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        List<Channel> channels = new ArrayList<>();
        if(user.isPresent()) {
            Optional<List<UserChannel>> userChannels = userChannelRepository.findAllByUser(user.get());
            if(userChannels.isPresent()) {
                for (UserChannel userChannel: userChannels.get()) {
                    channels.add(userChannel.getChannel());
                }
            }
        }

        return channels;
    }

    @Override
    public List<String> getChannelMember(String channelId) {
        Optional<Channel> channel = channelRepository.findByChannelId(channelId);
        List<String> memberNickList = new ArrayList<>();
        if(channel.isPresent()) {
            Optional<List<UserChannel>> userChannels = userChannelRepository.findAllByChannel(channel.get());
            if(userChannels.isPresent()) {
                for (UserChannel userChannel: userChannels.get()) {
                    memberNickList.add(userChannel.getUser().getNickname());
                }
            }
        }

        return memberNickList;
    }
}
