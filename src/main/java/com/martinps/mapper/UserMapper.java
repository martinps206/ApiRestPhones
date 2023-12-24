package com.martinps.mapper;

import com.martinps.dto.UserDTO;
import com.martinps.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toUserDto(User user);

    @Mapping(target = "password", ignore = true)
    User signUpToUser(UserDTO userDTO);

}

