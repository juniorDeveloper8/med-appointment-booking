package com.rober.dto;

import com.rober.entity.enums.Rols;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class UserInfo {

    private Integer id;
    private String name;
    private String lastName;
    private String email;
    private String password;
    private String specialty;
    private Rols rol;
    private Boolean status;


}
