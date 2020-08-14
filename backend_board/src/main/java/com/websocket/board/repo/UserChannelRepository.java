package com.websocket.board.repo;

import com.websocket.board.model.user.UserChannel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserChannelRepository extends JpaRepository<UserChannel, Long> {
}
