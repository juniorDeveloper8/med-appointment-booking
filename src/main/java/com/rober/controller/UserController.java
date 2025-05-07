package com.rober.controller;

import com.rober.dto.UserInfo;
import com.rober.dto.UserResponse;
import com.rober.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService service;

    @PostMapping
    public UserResponse createUser(@RequestBody UserInfo info){
        return service.createUser(info);
    }

    @PatchMapping("userId")
    public UserResponse updateUser(@PathVariable Integer userId, @RequestBody UserInfo info) {
        return  service.updateUser(userId, info);
    }

    @GetMapping("userId")
    public UserInfo getUserById(@PathVariable Integer userId){
        return service.getUserById(userId);
    }

    @GetMapping
    public UserInfo getAllUser(){
        return (UserInfo) service.getAllUsers();
    }

    @DeleteMapping("userId")
    public UserResponse deleteUser(@PathVariable Integer userId){
        return service.deleteUser(userId);
    }

}
