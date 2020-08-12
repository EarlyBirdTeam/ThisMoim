package com.websocket.board.repo;

import com.websocket.board.model.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {

    Boolean existsByUserId(Long userId);
}
