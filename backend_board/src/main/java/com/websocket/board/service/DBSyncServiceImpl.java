package com.websocket.board.service;

import com.websocket.board.dto.Channel;
import com.websocket.board.model.SocketBoardMessage;
import com.websocket.board.repo.PostitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DBSyncServiceImpl implements DBSyncService {

    @Autowired
    PostitRepository postitRepository;

    @Override
    public void postitDBSync(SocketBoardMessage board) {
        for (int i = 0; i < board.getPostitList().size(); i++) {
            postitRepository.save(board.getPostitList().get(i));
        }
    }

}
