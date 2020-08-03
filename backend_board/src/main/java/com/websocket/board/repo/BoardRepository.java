package com.websocket.board.repo;

import com.websocket.board.dto.Board;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends CrudRepository<Board, Long> {
        Board findByChannelId(String channelId);
}
