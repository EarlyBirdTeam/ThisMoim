package com.websocket.board.service;

import com.websocket.board.dto.Channel;
import com.websocket.board.model.SocketBoardMessage;

public interface DBSyncService {

    void postitDBSync(SocketBoardMessage board);
    void channelDBIdCountSync(String channelId, Long idCount);

}
