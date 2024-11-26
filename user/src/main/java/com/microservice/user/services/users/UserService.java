package com.microservice.user.services.users;

import com.microservice.user.entities.User;
import com.microservice.user.models.users.LoginReq;
import com.microservice.user.models.users.RefreshTokenReq;
import com.microservice.user.models.users.RegisterReq;
import com.microservice.user.models.users.TokenRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    
    User register(RegisterReq req);
    
    TokenRes login(LoginReq dto);
    
    TokenRes refreshToken(RefreshTokenReq req);
    
    Page<User> getAll(Pageable pageable);
    
    User getByUsername(String username);
 
    boolean existByUsernameAndId(String username, Long id);
    
    User getById(Long id);
}
