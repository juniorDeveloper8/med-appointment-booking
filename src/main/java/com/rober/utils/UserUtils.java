package com.rober.utils;

import com.rober.dto.UserInfo;
import com.rober.entity.User;

public class UserUtils {
    // ✅ Usuario creado
    public static final String USUARIO_CREADO_CODE = "010";
    public static final String USUARIO_CREADO_MSG = "Usuario creado exitosamente.";

    // ✅ Usuario actualizado
    public static final String USUARIO_ACTUALIZADO_CODE = "011";
    public static final String USUARIO_ACTUALIZADO_MSG = "Usuario actualizado correctamente.";

    // ✅ Usuario eliminado
    public static final String USUARIO_ELIMINADO_CODE = "012";
    public static final String USUARIO_ELIMINADO_MSG = "Usuario eliminado exitosamente.";

    // ⚠️ Usuario no encontrado
    public static final String USUARIO_NO_ENCONTRADO_CODE = "013";
    public static final String USUARIO_NO_ENCONTRADO_MSG = "Usuario no encontrado.";

    // ⚠️ Error general
    public static final String USUARIO_ERROR_CODE = "014";
    public static final String USUARIO_ERROR_MSG = "Ocurrió un error al procesar la solicitud del usuario.";


    // ✅ Metodo de mapeo estatico
    public static UserInfo mapToDto(User user) {
        return UserInfo.builder()
                .id(user.getId())
                .name(user.getName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .rol(user.getRol())
                .status(user.getStatus())
                .specialty(user.getSpecialty())
                .build();
    }

}

