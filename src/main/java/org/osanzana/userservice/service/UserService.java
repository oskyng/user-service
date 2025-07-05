package org.osanzana.userservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.osanzana.userservice.dto.CreateUserRequest;
import org.osanzana.userservice.dto.CreateUserResponse;
import org.osanzana.userservice.dto.PhoneDto;
import org.osanzana.userservice.dto.UserDto;
import org.osanzana.userservice.exception.EmailAlreadyRegisteredException;
import org.osanzana.userservice.model.Phone;
import org.osanzana.userservice.model.User;
import org.osanzana.userservice.repository.IUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public CreateUserResponse createUser(CreateUserRequest request) {
        Optional<User> foundUser = userRepository.findByEmail(request.getEmail());
        if (foundUser.isPresent()) {
            throw new EmailAlreadyRegisteredException("User with email " + request.getEmail() + " already exists");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhones(request.getPhones().stream()
                .map(phoneDto -> {
                    Phone phone = new Phone();
                    phone.setNumber(phoneDto.getNumber());
                    phone.setCityCode(phoneDto.getCitycode());
                    phone.setCountryCode(phoneDto.getContrycode());
                    return phone;
                }).collect(Collectors.toList()));

        user.setToken(UUID.randomUUID().toString());
        user.setCreated(LocalDateTime.now());
        user.setModified(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());
        user.setActive(true);
        User saveUser = userRepository.save(user);

        CreateUserResponse response = new CreateUserResponse();
        response.setUser(mapToUserDto(saveUser));
        return response;
    }

    private UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setCreated(user.getCreated());
        userDto.setModified(user.getModified());
        userDto.setLastLogin(user.getLastLogin());
        userDto.setToken(user.getToken());
        userDto.setActive(user.isActive());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setPhones(user.getPhones().stream()
                .map(phone -> {
                    PhoneDto phoneDto = new PhoneDto();
                    phoneDto.setNumber(phone.getNumber());
                    phoneDto.setCitycode(phone.getCityCode());
                    phoneDto.setContrycode(phone.getCountryCode());
                    return phoneDto;
                })
                .collect(Collectors.toList()));
        return userDto;
    }
}
