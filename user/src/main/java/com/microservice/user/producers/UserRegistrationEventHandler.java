package com.microservice.user.producers;

import com.microservice.user.models.users.UserRes;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationEventHandler {
    
    private static final String MAIL_EXCHANGE = "mail-exchange";
    
    private static final String USER_REGISTRATION_MAIL_KEY = "user.registration.mail";
    
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    public void sendMailOnUserRegistration(UserRes user){
        rabbitTemplate.convertAndSend(MAIL_EXCHANGE, USER_REGISTRATION_MAIL_KEY, user);
    }

}
