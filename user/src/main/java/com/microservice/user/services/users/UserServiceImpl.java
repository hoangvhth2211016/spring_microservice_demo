package com.microservice.user.services.users;

import com.microservice.user.entities.Role;
import com.microservice.user.entities.User;
import com.microservice.user.exceptions.NotFoundException;
import com.microservice.user.exceptions.PasswordNotMatchException;
import com.microservice.user.models.roles.RoleType;
import com.microservice.user.models.users.LoginReq;
import com.microservice.user.models.users.RefreshTokenReq;
import com.microservice.user.models.users.RegisterReq;
import com.microservice.user.models.users.TokenRes;
import com.microservice.user.repositories.RoleRepo;
import com.microservice.user.repositories.UserRepo;
import com.microservice.user.utils.JwtUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;
    
    @Autowired
    private RoleRepo roleRepo;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Override
    public User register(RegisterReq req) {
        User newUser = new User();
        newUser.setFirstName(req.getFirstName());
        newUser.setLastName(req.getLastName());
        newUser.setUsername(req.getUsername());
        newUser.setEmail(req.getEmail());
        newUser.setPassword(passwordEncoder.encode(req.getPassword()));
        Role role = roleRepo.findByName(RoleType.USER.name()).orElseThrow(() -> new NotFoundException("Role not found"));
        newUser.getRoles().add(role);
        return userRepo.save(newUser);
    }
    
    @Override
    public TokenRes login(LoginReq req) {
        User currentUser = getByUsername(req.getUsername());
        if(!passwordEncoder.matches(CharBuffer.wrap(req.getPassword()), currentUser.getPassword())) throw new PasswordNotMatchException();
        return TokenRes.builder()
                .user(currentUser)
                .accessToken(jwtUtil.createAccessToken(currentUser))
                .refreshToken(jwtUtil.createRefreshToken(currentUser))
                .build();
    }
    
    @Override
    public TokenRes refreshToken(RefreshTokenReq req) {
        String username = jwtUtil.extractUsername(req.getRefreshToken());
        User currentUser = getByUsername(username);
        return TokenRes.builder()
                .user(currentUser)
                .accessToken(jwtUtil.createAccessToken(currentUser))
                .refreshToken(req.getRefreshToken())
                .build();
    }
    
    @Override
    public Page<User> getAll(Pageable pageable) {
        return userRepo.findAll(pageable);
    }
    
    @Override
    public User getByUsername(String username) {
        return userRepo.findByUsername(username).orElseThrow(()-> new NotFoundException("User not found"));
    }
    
    @Override
    public boolean existByUsernameAndId(String username, Long id) {
        return userRepo.existsByUsernameAndId(username, id);
    }
    
    @Override
    public User getById(Long id) {
        return userRepo.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }
    
}
