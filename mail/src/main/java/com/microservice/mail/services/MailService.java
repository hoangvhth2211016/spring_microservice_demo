package com.microservice.mail.services;

import com.microservice.mail.models.MailTemplate;

public interface MailService {
    
    void send(MailTemplate mailTemplate);
    
}
