package org.osanzana.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.osanzana.userservice.dto.CreateUserRequest;
import org.osanzana.userservice.dto.CreateUserResponse;
import org.osanzana.userservice.exception.CustomErrorResponse;
import org.osanzana.userservice.exception.EmailAlreadyRegisteredException;
import org.osanzana.userservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController implements IUserController {
    private final UserService userService;

    @Override
    public ResponseEntity<?> registerUser(CreateUserRequest createUserRequest) {
        try {
            CreateUserResponse userResponse = userService.createUser(createUserRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
        } catch (EmailAlreadyRegisteredException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new CustomErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CustomErrorResponse("Ocurrió un error interno."));
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String errorMessage = error.getDefaultMessage();
            errors.put("mensaje", errorMessage);
        });
        return errors;
    }
}
