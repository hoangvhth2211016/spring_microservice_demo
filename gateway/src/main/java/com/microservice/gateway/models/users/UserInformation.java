package com.microservice.gateway.models.users;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UserInformation implements Serializable {
    
    private Long id;
    
    private String firstName;
    
    private String lastName;
    
    private String username;
    
    private String email;
    
    private Set<String> roles = new HashSet<>();
    
}
