package org.osanzana.userservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.osanzana.userservice.dto.CreateUserRequest;
import org.osanzana.userservice.exception.EmailAlreadyRegisteredException;
import org.osanzana.userservice.model.User;
import org.osanzana.userservice.repository.IUserRepository;
import org.osanzana.userservice.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    @Mock
    private IUserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);;
    }

    @Test
    void testCreateUserEmailAlreadyRegistered() {
        CreateUserRequest request = new CreateUserRequest();
        request.setName("Jane Doe");
        request.setEmail("jane.doe@example.com");
        request.setPassword("Password123");
        request.setPhones(Collections.emptyList());

        User existingUser = new User();
        existingUser.setEmail(request.getEmail());

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(existingUser));

        EmailAlreadyRegisteredException thrown = assertThrows(EmailAlreadyRegisteredException.class, () -> {
            userService.createUser(request);
        });

        assertEquals("User with email " + request.getEmail() + " already exists", thrown.getMessage());
    }
}
