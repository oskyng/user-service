package org.osanzana.userservice.service;

import org.osanzana.userservice.dto.CreateUserRequest;
import org.osanzana.userservice.dto.CreateUserResponse;

public interface IUserService {
    CreateUserResponse createUser(CreateUserRequest request);
}
