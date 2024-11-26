package com.microservice.user.mappers;

import com.microservice.user.entities.Role;
import com.microservice.user.entities.User;
import com.microservice.user.models.users.RegisterReq;
import com.microservice.user.models.users.UserRes;
import org.mapstruct.*;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    
    @Mapping(source = "roles", target = "roles", qualifiedByName = "roleSetToStringSet")
    UserRes toUserRes(User user);
    
    User fromRegisterReq(RegisterReq registerReq);
    
    @Named("roleSetToStringSet")
    default Set<String> roleSetToStringSet(Set<Role> roles) {
        if (roles.isEmpty()) {
            return null;
        }
        return roles.stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
    }
    
}