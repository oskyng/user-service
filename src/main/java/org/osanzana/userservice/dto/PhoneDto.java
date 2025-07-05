package org.osanzana.userservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PhoneDto {
    @NotBlank(message = "El número de teléfono no puede estar vacío")
    private String number;
    @NotBlank(message = "El código de ciudad no puede estar vacío")
    private String citycode;
    @NotBlank(message = "El código de país no puede estar vacío")
    private String contrycode;
}
