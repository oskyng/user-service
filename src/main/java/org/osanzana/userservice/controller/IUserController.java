package org.osanzana.userservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.osanzana.userservice.dto.AuthRequest;
import org.osanzana.userservice.dto.AuthResponse;
import org.osanzana.userservice.dto.CreateUserRequest;
import org.osanzana.userservice.dto.CreateUserResponse;
import org.osanzana.userservice.exception.CustomErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/users")
@Tag(name = "User Management")
public interface IUserController {
    @Operation(summary = "Register a new user", description = "Creates a new user account with provided details, including name, email, password, and phones.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CreateUserResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Email already registered", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)))
    })
    @PostMapping("/register")
    ResponseEntity<?> registerUser(@Valid @RequestBody CreateUserRequest createUserRequest);
}
