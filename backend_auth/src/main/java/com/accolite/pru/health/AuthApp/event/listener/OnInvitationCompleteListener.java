package com.accolite.pru.health.AuthApp.event.listener;

import com.accolite.pru.health.AuthApp.event.OnInvitationCompleteEvent;
import com.accolite.pru.health.AuthApp.exception.MailSendException;
import com.accolite.pru.health.AuthApp.model.User;
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
public class OnInvitationCompleteListener implements ApplicationListener<OnInvitationCompleteEvent> {

    private static final Logger logger = Logger.getLogger(OnUserRegistrationCompleteListener.class);
    private final MailService mailService;

    @Autowired
    public OnInvitationCompleteListener(MailService mailService) {
        this.mailService = mailService;
    }


    @Override
    @Async
    public void onApplicationEvent(OnInvitationCompleteEvent onInvitationCompleteEvent) {
        sendEmailVerification(onInvitationCompleteEvent);
    }

    private void sendEmailVerification(OnInvitationCompleteEvent event) {
        User user = event.getMember().getUser();

        String recipientAddress = user.getEmail();
        String emailConfirmationUrl =
                event.getRedirectUrl().toUriString();
        System.out.println("--------------------");
        try {
            mailService.sendInviteEmail(emailConfirmationUrl, recipientAddress);
        } catch (IOException | TemplateException | MessagingException e) {
            logger.error(e);
            throw new MailSendException(recipientAddress, "Email Verification");
        }
    }
}
