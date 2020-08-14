/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.accolite.pru.health.AuthApp.event.listener;



import com.accolite.pru.health.AuthApp.event.OnInvitationEvent;
import com.accolite.pru.health.AuthApp.event.OnRegenerateEmailVerificationEvent;
import com.accolite.pru.health.AuthApp.event.OnUserRegistrationCompleteEvent;
import com.accolite.pru.health.AuthApp.exception.MailSendException;
import com.accolite.pru.health.AuthApp.model.User;
import com.accolite.pru.health.AuthApp.model.member.Member;
import com.accolite.pru.health.AuthApp.service.MailService;
import freemarker.template.TemplateException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.IOException;

@Component
public class OnInvitationListener implements ApplicationListener<OnInvitationEvent> {

    private static final Logger logger = Logger.getLogger(OnInvitationListener.class);
    private final MailService mailService;

    @Autowired
    public OnInvitationListener(MailService mailService) {
        this.mailService = mailService;
    }


    @Override
    @Async
    public void onApplicationEvent( OnInvitationEvent onInvitationEvent) {
        sendEmailVerification(onInvitationEvent);
    }


    private void resendEmailVerification(OnInvitationEvent event) {
        String channelId = event.getMailSendRequest().getChannelId();
        String recipientAddress = channelId;

        String inviteConfirmUrl = event.getRedirectUrl().toUriString();
        try {
            mailService.sendInviteEmail(inviteConfirmUrl, recipientAddress);
        } catch (IOException | TemplateException | MessagingException e) {
            logger.error(e);
            throw new MailSendException(recipientAddress, "Email Verification");
        }
    }
    private void sendEmailVerification(OnInvitationEvent event) {
        String recipientAddress = event.getMailSendRequest().getEmail();
        String emailConfirmationUrl =
                event.getRedirectUrl().toUriString();

        try {
            mailService.sendEmailVerification(emailConfirmationUrl, recipientAddress);
        } catch (IOException | TemplateException | MessagingException e) {
            logger.error(e);
            throw new MailSendException(recipientAddress, "Email Verification");
        }
    }
}
