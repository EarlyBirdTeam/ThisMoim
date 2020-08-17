package com.accolite.pru.health.AuthApp.service;

import com.accolite.pru.health.AuthApp.model.User;
import com.accolite.pru.health.AuthApp.model.member.Member;
import com.accolite.pru.health.AuthApp.model.payload.ChannelRequest;
import com.accolite.pru.health.AuthApp.model.payload.ChannelResponse;
import com.accolite.pru.health.AuthApp.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MemberService {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    UserService userService;

    @Autowired
    ApiService<ChannelResponse> apiService;

    public List<Member> findByChannelId(String channelId) {
        return memberRepository.findAllByChannelId(channelId).orElseThrow(() -> new NoSuchElementException());
    }

    public Member createMember(User user, String channelId) {
        Member newMember = new Member();
        newMember.setChannelId(channelId);
        newMember.setUser(user);
        System.out.println(user.getEmail()+ " emmmail");
        newMember.setAttendance(0);
        newMember.setMemberRoleName("MEMBER_NORMAL");
        newMember.setEmail(user.getEmail());
        return memberRepository.save(newMember);
    }

    public Member isMemberExist(String email, String channelId){
        return memberRepository.findByEmailAndChannelId(email,channelId).orElse(null);
    }

    // 사용자 정보 + 채널 정보 POST
    public ChannelResponse callPostBoardServer(ChannelRequest channelRequest) {
        String channelId = channelRequest.getChannelId();
        return apiService.post("http://i3a510.p.ssafy.io/api/login/board/channel/invitation"
                , HttpHeaders.EMPTY, channelRequest,ChannelResponse.class).getBody();
    }

}
