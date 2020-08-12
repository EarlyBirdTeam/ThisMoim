package com.websocket.board.service;

import com.websocket.board.model.member.Member;
import com.websocket.board.model.member.MemberRole;
import com.websocket.board.model.payload.UserToMemberRequest;
import com.websocket.board.repo.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class MemberService {
    @Autowired
    MemberRepository memberRepository;

    public Optional<Member> findById(Long Id) {
        return memberRepository.findById(Id);
    }

    public Member save(Member member) {
        return memberRepository.save(member);
    }

    public Boolean existsByUserId(Long userId) {
        return memberRepository.existsByUserId(userId);
    }

    public void deleteById(Long memberId) {
        Member member = this.findById(memberId).orElseThrow(() -> new NoSuchElementException());
        memberRepository.delete(member);
    }

//    public Member createMember(UserToMemberRequest userToMemberRequest) {
//        Member newMember = new Member();
//
//        return newMember;
//    }

}
