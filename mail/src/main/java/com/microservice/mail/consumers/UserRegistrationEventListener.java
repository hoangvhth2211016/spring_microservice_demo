package com.microservice.mail.consumers;

import com.microservice.mail.models.MailTemplate;
import com.microservice.mail.models.users.UserRes;
import com.microservice.mail.services.MailService;
import com.microservice.mail.utils.FileUtil;
import com.microservice.mail.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UserRegistrationEventListener {
    
    private static final Logger logger = LoggerFactory.getLogger(UserRegistrationEventListener.class);
    
    @Autowired
    private MailService mailService;
    
    @RabbitListener(queues = "user-registration-mail-queue")
    public void sendMailOnUserRegistration(UserRes user){
        logger.info("Processing mail request of user -> {}", user);
        
        StringBuilder template = new StringBuilder(FileUtil.readTemplate("userRegisterMail.html"));
        
        StringUtil.replace(template, Map.of(
                "${firstName}", user.getFirstName(),
                "${lastName}", user.getLastName(),
                "${username}", user.getUsername(),
                "${email}", user.getEmail()
        ));
        
        MailTemplate mailTemplate = MailTemplate.builder()
                .to(user.getEmail())
                .subject("User Registration")
                .body(template.toString())
                .build();
        mailService.send(mailTemplate);
    }
    
}
