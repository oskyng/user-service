package org.osanzana.userservice.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class UserDto {
    private UUID id;
    private LocalDateTime created;
    private LocalDateTime modified;
    private LocalDateTime lastLogin;
    private String token;
    private boolean isActive;
    private String name;
    private String email;
    private List<PhoneDto> phones;
}
