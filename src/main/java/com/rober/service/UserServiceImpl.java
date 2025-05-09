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

    // testing of method for moment
    @Override
    public UserResponse createUser(UserInfo request) {
        Rols rol = request.getRol();
        if (rol == null) {
            rol = Rols.PASIENTE;
            request.setRol(rol);
        }

        // Validaciones según el rol
        switch (rol) {
            case ADMIN:
                validarAdmin(request);
                break;
            case PASIENTE:
                validarPaciente(request);
                break;
            case DOCTOR:
                validarDoctor(request);
                break;
            default:
                throw new IllegalArgumentException("Rol no reconocido: " + rol);
        }

        // Crear y guardar usuario
        User user = User.builder()
                .name(request.getName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(request.getPassword())
                .rol(rol)
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
        User user = repository.findByIdAndStatusTrue(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado o inactivo"));

        // Validar que no se intente cambiar el rol
        if (request.getRol() != null && !request.getRol().equals(user.getRol())) {
            throw new UnsupportedOperationException("No está permitido cambiar el rol del usuario.");
        }

        // Actualizar campos según el rol actual
        switch (user.getRol()) {
            case PASIENTE:
                user.setName(request.getName());
                user.setLastName(request.getLastName());
                user.setEmail(request.getEmail());
                user.setPassword(request.getPassword());
                break;

            case DOCTOR:
                user.setName(request.getName());
                user.setLastName(request.getLastName());
                user.setEmail(request.getEmail());
                user.setPassword(request.getPassword());
                user.setSpecialty(request.getSpecialty());
                break;

            default:
                throw new UnsupportedOperationException("Actualización no permitida para el rol: " + user.getRol());
        }

        repository.save(user);

        return UserResponse.builder()
                .responseCode(UserUtils.USUARIO_ACTUALIZADO_CODE)
                .responseMessage(UserUtils.USUARIO_ACTUALIZADO_MSG)
                .status("ACTUALIZADO")
                .build();
    }

    @Override
    public UserInfo getUserById(Integer userId) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new RuntimeException(UserUtils.USUARIO_NO_ENCONTRADO_MSG));

        return UserUtils.mapByRol(user);
    }

    @Override
    public List<UserInfo> getAllUsers() {
        List<User> users = repository.findByStatusTrue();

        return users.stream()
                .map(UserUtils::mapByRol)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse deleteUser(Integer userId, Rols currentUserRol) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new RuntimeException(UserUtils.USUARIO_NO_ENCONTRADO_MSG));

        if (user.getRol() == Rols.PASIENTE) {
            // El PACIENTE puede eliminar su propia cuenta o un ADMIN puede eliminarla
            if (currentUserRol != Rols.PASIENTE && currentUserRol != Rols.ADMIN) {
                throw new UnsupportedOperationException("Solo un PACIENTE o un ADMIN puede eliminar la cuenta de un PACIENTE.");
            }
        }

        if (user.getRol() == Rols.DOCTOR) {
            // Solo un ADMIN puede eliminar la cuenta de un DOCTOR
            if (currentUserRol != Rols.ADMIN) {
                throw new UnsupportedOperationException("Solo un ADMIN puede eliminar la cuenta de un DOCTOR.");
            }
        }

        user.setStatus(false);
        repository.save(user);

        return UserResponse.builder()
                .responseCode(UserUtils.USUARIO_ELIMINADO_CODE)
                .responseMessage(UserUtils.USUARIO_ELIMINADO_MSG)
                .status("DESACTIVADO")
                .build();
    }

   /* @Override
    public UserResponse deleteUser(Integer userId) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new RuntimeException(UserUtils.USUARIO_NO_ENCONTRADO_MSG));

        user.setStatus(false);
        repository.save(user);

        return UserResponse.builder()
                .responseCode(UserUtils.USUARIO_ELIMINADO_CODE)
                .responseMessage(UserUtils.USUARIO_ELIMINADO_MSG)
                .status("DESACTIVADO")
                .build();
    }*/


    // method of create account
    private void validarAdmin(UserInfo request) {
        if (isNullOrEmpty(request.getEmail()) ||
                isNullOrEmpty(request.getPassword())) {
            throw new IllegalArgumentException("Email y contraseña son obligatorios para el rol ADMIN.");
        }
    }

    private void validarPaciente(UserInfo request) {
        if (isNullOrEmpty(request.getName()) ||
                isNullOrEmpty(request.getLastName()) ||
                isNullOrEmpty(request.getEmail()) ||
                isNullOrEmpty(request.getPassword())) {
            throw new IllegalArgumentException("Todos los campos excepto 'specialty' son obligatorios para el rol PACIENTE.");
        }
    }

    private void validarDoctor(UserInfo request) {
        if (isNullOrEmpty(request.getName()) ||
                isNullOrEmpty(request.getLastName()) ||
                isNullOrEmpty(request.getEmail()) ||
                isNullOrEmpty(request.getPassword()) ||
                isNullOrEmpty(request.getSpecialty())) {
            throw new IllegalArgumentException("Todos los campos son obligatorios para el rol DOCTOR.");
        }
    }

    private boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }


}
