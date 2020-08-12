package com.websocket.board.repo.member;

import com.websocket.board.model.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

}