package com.accolite.pru.health.AuthApp.service;

import com.accolite.pru.health.AuthApp.model.User;
import com.accolite.pru.health.AuthApp.model.member.Member;
import com.accolite.pru.health.AuthApp.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MemberService {

    @Autowired
    MemberRepository memberRepository;

    public List<Member> findByChannelId(String channelId) {
        return memberRepository.findAllByChannel(channelId).orElseThrow(() -> new NoSuchElementException());
    }

    public Member createMember(User user, String channelId) {
        Member newMember = new Member();
        newMember.setChannel(channelId);
        newMember.setUser(user);
        newMember.setAttendance(0);
        return memberRepository.save(newMember);
    }
}
