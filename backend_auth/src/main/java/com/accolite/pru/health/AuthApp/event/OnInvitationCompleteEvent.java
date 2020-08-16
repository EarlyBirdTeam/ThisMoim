package com.accolite.pru.health.AuthApp.event;

import com.accolite.pru.health.AuthApp.model.User;
import com.accolite.pru.health.AuthApp.model.member.Member;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
import org.springframework.web.util.UriComponentsBuilder;

@Getter
@Setter
public class OnInvitationCompleteEvent  extends ApplicationEvent {
    private transient UriComponentsBuilder redirectUrl;
    private Member member;

    public OnInvitationCompleteEvent(Member member, UriComponentsBuilder redirectUrl) {
        super(member);
        this.member = member;
        this.redirectUrl = redirectUrl;
    }

}