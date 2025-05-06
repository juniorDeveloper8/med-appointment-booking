package com.rober.service;

import com.rober.dto.UserInfo;
import com.rober.dto.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserInfo request);

    UserResponse updateUser(Integer userId, UserInfo request);

    UserInfo getUserById(Integer userId);

    List<UserInfo> getAllUsers();

    UserResponse deleteUser(Integer userId);
}
