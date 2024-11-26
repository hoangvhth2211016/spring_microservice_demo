package com.microservice.user.controllers;


import com.microservice.user.entities.User;
import com.microservice.user.mappers.UserMapper;
import com.microservice.user.models.pages.PageRes;
import com.microservice.user.models.users.RegisterReq;
import com.microservice.user.models.users.UserRes;
import com.microservice.user.services.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserMapper userMapper;
 
    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public PageRes<User> getAll(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ){
        return new PageRes<>(userService.getAll(pageable));
    }
    
    @GetMapping("profile")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public User getProfile(@AuthenticationPrincipal User user){
        return user;
    }
    
    @GetMapping("public/{id}")
    public UserRes getById(@PathVariable Long id){
        return userMapper.toUserRes(userService.getById(id));
    }
    
}
