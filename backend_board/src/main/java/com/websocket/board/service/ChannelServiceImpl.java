package com.websocket.board.service;

import com.websocket.board.model.Channel;
import com.websocket.board.model.user.User;
import com.websocket.board.model.user.UserChannel;
import com.websocket.board.repo.ChannelRepository;
import com.websocket.board.repo.UserChannelRepository;
import com.websocket.board.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public Channel saveChannel(String token, String channelName, String channelId) {

        Channel channel = new Channel().builder()
                .channelId(channelId)
                .channelName(channelName)
                .build();

        Channel channelResponse = channelRepository.save(channel);

        //token = "Bearer " + token;
        Optional<User> user = userRepository.findByToken(token);
        if("Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNTk3NDA4MTM0LCJleHAiOjE1OTc0MDkwMzR9.kaDROmTqBCQE56snEbQwi6IdgyRTt_GUnFMuP0KO0zcJnZKr4BMzCp7HH8ELBwdp0bAMPVG3NvY28QylHrbXaQ".equals(token)) {
            System.out.println("equals");
        }

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
    public List<Channel> getMyChannels(String token) {
        Optional<User> user = userRepository.findByToken(token);
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
}
