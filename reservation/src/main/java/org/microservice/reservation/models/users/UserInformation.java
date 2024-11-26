package org.microservice.reservation.models.users;

import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInformation implements Serializable {
    
    private Long id;
    
    private String firstName;
    
    private String lastName;
    
    private String username;
    
    private String email;
    
    private Set<String> roles = new HashSet<>();
    
}
