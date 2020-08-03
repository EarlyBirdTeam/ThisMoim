package com.websocket.board.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.websocket.board.dto.Board;
import org.springframework.stereotype.Service;

public interface DBSyncService {

    boolean boardDBSync(Board board);

}
