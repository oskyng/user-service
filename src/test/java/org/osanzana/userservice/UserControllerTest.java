package org.osanzana.userservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.osanzana.userservice.controller.UserController;
import org.osanzana.userservice.dto.CreateUserRequest;
import org.osanzana.userservice.dto.PhoneDto;
import org.osanzana.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @SuppressWarnings("removal")
    @MockBean
    private UserService userService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void registerUserInvalidEmailFormat() throws Exception {
        CreateUserRequest request = new CreateUserRequest();
        request.setName("John Doe");
        request.setEmail("invalid-email");
        request.setPassword("Password12345");
        PhoneDto phoneDto = new PhoneDto();
        phoneDto.setNumber("1234567890");
        phoneDto.setCitycode("1");
        phoneDto.setContrycode("57");
        request.setPhones(Collections.singletonList(phoneDto));

        mockMvc.perform(post("/v1/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensaje").value("Debe ser una dirección de correo bien formada (Ej. aaaaaaa@dominio.cl)."));
    }

    @Test
    void registerUserInvalidPasswordFormat() throws Exception {
        CreateUserRequest request = new CreateUserRequest();
        request.setName("John Doe");
        request.setEmail("john.doe@example.com");
        request.setPassword("weak");
        PhoneDto phoneDto = new PhoneDto();
        phoneDto.setNumber("1234567890");
        phoneDto.setCitycode("1");
        phoneDto.setContrycode("57");
        request.setPhones(Collections.singletonList(phoneDto));

        mockMvc.perform(post("/v1/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensaje").value("La contraseña debe tener una mayúscula, minúsculas y dos números."));
    }
}
