package com.microservice.user.controllers;

import com.microservice.user.entities.User;
import com.microservice.user.mappers.UserMapper;
import com.microservice.user.models.users.*;
import com.microservice.user.producers.UserRegistrationEventHandler;
import com.microservice.user.services.users.UserService;
import com.microservice.user.utils.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRegistrationEventHandler producer;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private UserRegistrationEventHandler userRegistrationEventHandler;
 
    @GetMapping("validation")
    public UserRes validateToken(@RequestParam("token") String token){
        String username = jwtUtil.extractUsername(token);
        User user = userService.getByUsername(username);
        return userMapper.toUserRes(user);
    }
    
    @PostMapping("register")
    public UserRes register(@RequestBody @Valid RegisterReq req){
        User user = userService.register(req);
        UserRes res = userMapper.toUserRes(user);
        userRegistrationEventHandler.sendMailOnUserRegistration(res);
        return res;
    }
    
    @PostMapping("login")
    public TokenRes login(@RequestBody @Valid LoginReq dto){
        return userService.login(dto);
    }
    
    @PostMapping("tokens")
    public TokenRes refreshToken(@RequestBody @Valid RefreshTokenReq req){
        return userService.refreshToken(req);
    }
    
}
