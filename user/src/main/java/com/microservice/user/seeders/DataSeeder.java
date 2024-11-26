package com.microservice.user.seeders;

import com.microservice.user.entities.Role;
import com.microservice.user.entities.User;
import com.microservice.user.models.roles.RoleType;
import com.microservice.user.repositories.RoleRepo;
import com.microservice.user.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class DataSeeder implements CommandLineRunner {
    
    @Autowired
    private UserRepo userRepo;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private RoleRepo roleRepo;
    
    
    @Override
    public void run(String... args) throws Exception {
        Role roleAdmin = seedRoles(RoleType.ADMIN);
        seedRoles(RoleType.USER);
        seedAdmin(roleAdmin);
    }
    
    @Transactional
    private void seedAdmin(Role role){
        Optional<User> admin = userRepo.findByUsername("admin");
        if(admin.isEmpty()){
            User user = new User();
            user.setFirstName("admin");
            user.setLastName("admin");
            user.setUsername("admin");
            user.setEmail("admin@gmail.com");
            user.setPassword(passwordEncoder.encode("admin"));
            user.getRoles().add(role);
            userRepo.save(user);
        }
    }
    
    private Role seedRoles(RoleType type){
        Optional<Role> thisRole = roleRepo.findByName(type.name());
        if(thisRole.isEmpty()){
            Role role = new Role();
            role.setName(type.name());
            return roleRepo.save(role);
        }
        return thisRole.get();
    }
}
