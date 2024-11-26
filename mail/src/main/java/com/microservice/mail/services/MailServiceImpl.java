package com.microservice.mail.services;

import com.microservice.mail.models.MailTemplate;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {
    
    @Value("${company.email.no-reply}")
    private String from;
    
    @Autowired
    private JavaMailSender javaMailSender;
    
    @Override
    @SneakyThrows
    public void send(MailTemplate mailTemplate) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        
        helper.setFrom(from);
        helper.setTo(mailTemplate.getTo());
        helper.setSubject(mailTemplate.getSubject());
        helper.setText(mailTemplate.getBody(), true);
        
        if(mailTemplate.getFile() != null && mailTemplate.getFileName() != null){
            helper.addAttachment(mailTemplate.getFileName(), new ByteArrayResource(mailTemplate.getFile()));
        }
        
        javaMailSender.send(message);
    }
}
