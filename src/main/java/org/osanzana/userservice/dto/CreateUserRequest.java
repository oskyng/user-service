package org.osanzana.userservice.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.List;

@Data
public class CreateUserRequest {
    @NotBlank(message = "El nombre no puede estar vacío")
    private String name;
    @NotBlank(message = "El correo no puede estar vacío")
    @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    private String email;
    @NotBlank(message = "La contraseña no puede estar vacía")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d.*\\d)[A-Za-z\\d]+$", message = "La contraseña debe tener una mayúscula, minúsculas y dos números.")
    private String password;
    @NotNull(message = "La lista de teléfonos no puede ser nula")
    @Valid
    private List<PhoneDto> phones;
}
