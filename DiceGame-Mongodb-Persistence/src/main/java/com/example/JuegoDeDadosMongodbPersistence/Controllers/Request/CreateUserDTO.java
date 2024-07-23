package com.example.JuegoDeDadosMongodbPersistence.Controllers.Request;

import com.example.JuegoDeDadosMongodbPersistence.Model.RoleEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDTO {

    @Email
    @NotBlank
    private String email;

    private String username;
    @NotBlank
    private String password;





}
