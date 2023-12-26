package com.martinps.service;

import com.martinps.dto.PhoneDTO;
import com.martinps.dto.UserDTO;
import com.martinps.entity.Phone;
import com.martinps.entity.User;
import com.martinps.mapper.UserMapper;
import com.martinps.exception.AppException;
import com.martinps.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    public UserDTO register(UserDTO userDTO){
        Optional<User> optionalUser = userRepository.findByEmail(userDTO.getEmail());

        if (optionalUser.isPresent()){
            throw new AppException("Login already exists", HttpStatus.BAD_REQUEST);
        }

        System.out.println("userDTO --> " + userDTO.toString());

        User user = userMapper.signUpToUser(userDTO);
        System.out.println("user --> " + user.toString());

        if (userDTO.getPhones() != null && !userDTO.getPhones().isEmpty()){
            Set<Phone> phones = new HashSet<>();
            for (Phone phoneDTO : userDTO.getPhones()){
                Phone phone = new Phone();
                phone.setNumber(phoneDTO.getNumber());
                phone.setCityCode(phoneDTO.getCityCode());
                phone.setCountryCode(phoneDTO.getCountryCode());
                phone.setUser(user);
                phones.add(phone);
            }
            user.setPhones(phones);
        }

        User savedUser = userRepository.save(user);
        System.out.println("savedUser --> " + savedUser.toString());

        return userMapper.toUserDto(savedUser);
    }




}
