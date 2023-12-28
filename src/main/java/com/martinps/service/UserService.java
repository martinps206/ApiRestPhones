package com.martinps.service;

import com.martinps.dto.UserDTO;
import com.martinps.entity.Phone;
import com.martinps.entity.User;
import com.martinps.mapper.UserMapper;
import com.martinps.exception.AppException;
import com.martinps.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    public UserDTO register(UserDTO userDTO){
        Optional<User> optionalUser = userRepository.findByEmail(userDTO.getEmail());
        if (optionalUser.isPresent()){
            throw new AppException("User already exists", HttpStatus.BAD_REQUEST);
        }
        System.out.println("userDTO --> " + userDTO.toString());
        User user = userMapper.signUpToUser(userDTO);

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
        return userMapper.toUserDto(savedUser);
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOs = new ArrayList<>();
        for (User user : users) {
            userDTOs.add(userMapper.toUserDto(user));
        }
        return userDTOs;
    }

    public UserDTO findUserById(String id){
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            return userMapper.toUserDto(userOptional.get());

        }else{
            throw new AppException("User doesn't exist", HttpStatus.BAD_REQUEST);
        }

    }

    public void deletedUser(String id){
        userRepository.deleteById(id);
    }

    public UserDTO updateUser(String id, UserDTO userDTO){
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            throw new AppException("User doesn't exist", HttpStatus.BAD_REQUEST);
        }

        User user = userMapper.signUpToUser(userDTO);
        userOptional.get().setId(user.getId());
        userOptional.get().setName(user.getName());
        userOptional.get().setEmail(user.getEmail());
        userOptional.get().setPassword(user.getPassword());


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

        User savedUser = userRepository.save(userOptional.get());
        return userMapper.toUserDto(savedUser);
    }


}
