package com.websocket.board.repo;

import com.websocket.board.model.SocketBoardMessage;
import com.websocket.board.model.calendar.Calendar;
import com.websocket.board.model.kanban.Kanban;
import lombok.RequiredArgsConstructor;
import com.websocket.board.dto.Channel;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ChannelRedisRepository {
    // Redis CacheKeys
    private static final String CHANNEL = "CHANNEL"; // 채널 저장
    public static final String USER_COUNT = "USER_COUNT"; // 채널에 입장한 클라이언트 수 저장
    public static final String ENTER_INFO = "ENTER_INFO"; // 채널에 입장한 클라이언트의 sessionId와 채널 id를 맵핑한 정보 저장
    public static final String BOARD_STATE = "BOARD_STATE"; // 채널이 가지고 있는 보드의 상태 저장

    @Resource(name = "redisTemplate")
    private HashOperations<String, String, Channel> hashOpsChannel;
    @Resource(name = "redisTemplate")
    private HashOperations<String, String, String> hashOpsEnterInfo;
    @Resource(name = "redisTemplate")
    private ValueOperations<String, String> valueOps;
    @Resource(name = "redisTemplate")
    private HashOperations<String, String, SocketBoardMessage> hashOpsBoard;

    // 모든 채널 조회
    public List<Channel> findAllChannel() {
        return hashOpsChannel.values(CHANNEL);
    }

    // 채널 생성 : 서버간? 채널 공유를 위해 redis hash에 저장한다.
    // -> 현 단계에서는 굳이 채널의 부하를 줄이기 위해 서버를 나누어 채널 정보를 공유하는 것이 중요하지 않은 작업이므로 보류
    public Channel createChannel(Channel channel) {
        //Channel channel = Channel.create(name);
        hashOpsChannel.put(CHANNEL, channel.getChannelId(), channel);
        // 채널당 보드가 생성되므로 채널을 생성할때 레디스에 채널 아이디를 hashkey 값으로 보드도 생성
        createBoard(channel.getChannelId());
        return channel;
    }

    // 보드 생성
    // 보드 리스트 널 값으로 가면 프론트 에러 발생 -> 빈 리스트 값 넣어주기
    private SocketBoardMessage createBoard(String channelId) {
        SocketBoardMessage board = new SocketBoardMessage()
                .builder()
                .channelId(channelId)
                .postitList(new ArrayList<>())
                .kanban(new Kanban())
                .calendar(new Calendar())
                .build();
        hashOpsBoard.put(BOARD_STATE, channelId, board);
        return board;
    }

    public Channel updateChannel(Channel channel) {
        hashOpsChannel.put(CHANNEL, channel.getChannelId(), channel);
        return channel;
    }

    public SocketBoardMessage updateBoard(SocketBoardMessage board) {
        hashOpsBoard.put(BOARD_STATE, board.getChannelId(), board);
        return board;
    }

    // 특정 채널 조회
    public Channel findChannelById(String id) {
        return hashOpsChannel.get(CHANNEL, id);
    }

    // 특정 채널의 보드 조회
    public SocketBoardMessage findBoardByChannelId(String id) {
        return hashOpsBoard.get(BOARD_STATE, id);
    }

    // 유저가 입장한 채널ID와 유저 세션ID 맵핑 정보 저장
    public void setUserEnterInfo(String sessionId, String channelId) {
        hashOpsEnterInfo.put(ENTER_INFO, sessionId, channelId);
    }

    // 유저 세션으로 입장해 있는 채널 ID 조회
    public String getUserEnterChannelId(String sessionId) {
        return hashOpsEnterInfo.get(ENTER_INFO, sessionId);
    }

    // 유저 세션정보와 맵핑된 채널ID 삭제
    public void removeUserEnterInfo(String sessionId) {
        hashOpsEnterInfo.delete(ENTER_INFO, sessionId);
    }

    // 채팅방 유저수 조회
    public long getUserCount(String channelId) {
        return Long.valueOf(Optional.ofNullable(valueOps.get(USER_COUNT + "_" + channelId))
                .orElse("0"));
    }

    // 채팅방에 입장한 유저수 +1
    public long plusUserCount(String channelId) {
        return Optional
                .ofNullable(valueOps.increment(USER_COUNT + "_" + channelId))
                .orElse(0L);
    }

    // 채팅방에 입장한 유저수 -1
    public long minusUserCount(String channelId) {
        return Optional
                .ofNullable(valueOps.decrement(USER_COUNT + "_" + channelId))
                .filter(count -> count > 0)
                .orElse(0L);
    }
}
