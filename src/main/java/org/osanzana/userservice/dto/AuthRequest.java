package org.osanzana.userservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthRequest {
    @NotBlank(message = "El correo no puede estar vacío")
    private String email;

    @NotBlank(message = "La contraseña no puede estar vacía")
    private String password;
}
