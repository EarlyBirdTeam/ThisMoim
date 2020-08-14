package com.accolite.pru.health.AuthApp.event.listener;

import com.accolite.pru.health.AuthApp.event.OnInvitationCompleteEvent;
import com.accolite.pru.health.AuthApp.event.OnUserRegistrationCompleteEvent;
import com.accolite.pru.health.AuthApp.exception.MailSendException;
import com.accolite.pru.health.AuthApp.model.User;
import com.accolite.pru.health.AuthApp.service.EmailVerificationTokenService;
import com.accolite.pru.health.AuthApp.service.MailService;
import freemarker.template.TemplateException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.util.UriComponentsBuilder;

import javax.mail.MessagingException;
import java.io.IOException;

public class OnInvitationCompleteListner implements ApplicationListener<OnInvitationCompleteEvent> {

    private static final Logger logger = Logger.getLogger(OnUserRegistrationCompleteListener.class);
    private final MailService mailService;

    @Autowired
    public OnInvitationCompleteListner(MailService mailService) {
        this.mailService = mailService;
    }

    /**
     * As soon as a registration event is complete, invoke the email verification
     * asynchronously in an another thread pool
     */
    @Override
    @Async
    public void onApplicationEvent(OnInvitationCompleteEvent onInvitationCompleteEvent) {
        sendEmailVerification(onInvitationCompleteEvent);
    }

    /**
     * Send email verification to the user and persist the token in the database.
     */
    private void sendEmailVerification(OnInvitationCompleteEvent event) {
        User user = event.getMember().getUser();

        String recipientAddress = user.getEmail();
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
