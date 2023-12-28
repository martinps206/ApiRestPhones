package com.martinps.controller;

import com.martinps.dto.UserDTO;
import com.martinps.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import com.martinps.service.UserService;

import java.net.URI;
import java.util.List;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody @Valid UserDTO userDTO){
        UserDTO createdUser = userService.register(userDTO);
        return ResponseEntity.created(URI.create("/user/" + createdUser.getId())).body(createdUser);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        List<UserDTO> userDTOS = userService.getAllUsers();

        if (!userDTOS.isEmpty()) {
            return new ResponseEntity<>(userDTOS, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable String id){
        UserDTO userDTO = userService.findUserById(id);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> deletedUser(@PathVariable String id){
        try {
            userService.deletedUser(id);
            return ResponseEntity.ok("Deleted user");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed deleted user" + e.getMessage());
        }
    }

    @PutMapping("user/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable String id, @RequestBody UserDTO userDTO){
        UserDTO userUpdate = userService.updateUser(id, userDTO);
        //return ResponseEntity.ok(userUpdate);
        return new ResponseEntity<>(userUpdate, HttpStatus.OK);
    }

}
