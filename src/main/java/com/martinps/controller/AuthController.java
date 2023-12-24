package com.martinps.controller;

import com.martinps.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.martinps.service.UserService;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody @Valid UserDTO userDTO){
        if (userDTO.getEmail() == null) {
            System.out.println("no hay datos----->");
        }

        UserDTO createdUser = userService.register(userDTO);

        return ResponseEntity.created(URI.create("/user/" + createdUser.getId())).body(createdUser);

    }
}
