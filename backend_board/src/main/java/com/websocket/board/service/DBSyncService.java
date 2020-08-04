package com.websocket.board.service;

import com.websocket.board.model.SocketBoardMessage;

public interface DBSyncService {

    void postitDBSync(SocketBoardMessage board);

}
