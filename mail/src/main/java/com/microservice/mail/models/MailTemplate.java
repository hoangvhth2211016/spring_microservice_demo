package com.microservice.mail.models;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MailTemplate implements Serializable {
    
    String to;
    
    String subject;
    
    String body;
    
    byte[] file;
    
    String fileName;
    
}
