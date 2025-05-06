package com.rober.service;

import com.rober.dto.UserInfo;
import com.rober.dto.UserResponse;
import com.rober.entity.User;
import com.rober.entity.enums.Rols;
import com.rober.repositories.UserRepository;
import com.rober.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository repository;

    @Override
    public UserResponse createUser(UserInfo request) {
        User user = User.builder()
                .name(request.getName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(request.getPassword())
                .rol(request.getRol())
                .specialty(request.getSpecialty())
                .status(true)
                .build();

        repository.save(user);

        return UserResponse.builder()
                .responseCode(UserUtils.USUARIO_CREADO_CODE)
                .responseMessage(UserUtils.USUARIO_CREADO_MSG)
                .status("CREADO")
                .build();

    }

    @Override
    public UserResponse updateUser(Integer userId, UserInfo request) {

        User user = repository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        user.setName(request.getName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setSpecialty(request.getSpecialty());
        user.setRol(request.getRol());
        repository.save(user);

        return UserResponse.builder()
                .responseCode(UserUtils.USUARIO_ACTUALIZADO_CODE)
                .responseMessage(UserUtils.USUARIO_ACTUALIZADO_CODE)
                .status("ACTUALIZADO")
                .build();
    }

    @Override
    public UserInfo getUserById(Integer userId) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new RuntimeException(UserUtils.USUARIO_NO_ENCONTRADO_MSG));

        return UserUtils.mapToDto(user);
    }

    @Override
    public List<UserInfo> getAllUsers() {
        List<User> users = repository.findAll();

        return users.stream()
                .map(UserUtils::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse deleteUser(Integer userId) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new RuntimeException(UserUtils.USUARIO_NO_ENCONTRADO_MSG));

        repository.delete(user);

        return UserResponse.builder()
                .responseCode(UserUtils.USUARIO_ELIMINADO_CODE)
                .responseMessage(UserUtils.USUARIO_ELIMINADO_MSG)
                .status("ELIMINADO")
                .build();
    }
}
