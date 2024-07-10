package com.example.Blogging.App.Backend.controllers;

import com.example.Blogging.App.Backend.Entity.UserEntity;
import com.example.Blogging.App.Backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping()
    public List<UserEntity> getAllUser() {
        return userService.getAllUser();
    }

    @PostMapping("/user")
    public UserEntity addUser(@RequestBody UserEntity user) {
        return userService.createUser(user);
    }

    @GetMapping("/{userId}")
    public UserEntity getUserById(@PathVariable Integer userId) throws Exception {
        return userService.getUserById(userId);
    }

    @PutMapping("/follow/{userId1}/{userId2}")
    public Boolean followUser(@PathVariable Integer userId1, @PathVariable Integer userId2) throws Exception {
        return userService.followUser(userId1, userId2);
    }
}
