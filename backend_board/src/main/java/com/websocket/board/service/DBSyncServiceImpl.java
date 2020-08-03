package com.websocket.board.service;

import com.websocket.board.dto.Board;
import com.websocket.board.repo.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DBSyncServiceImpl implements DBSyncService {

    @Autowired
    BoardRepository boardRepository;

    @Override
    public boolean boardDBSync(Board board) {

        // need refactoring :-(
        board.setId(boardRepository.findByChannelId(board.getChannelId()).getId());

        if (boardRepository.save(board) != null) {
            return true;
        }
        return false;
    }
}
