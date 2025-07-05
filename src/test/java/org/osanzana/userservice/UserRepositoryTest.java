package org.osanzana.userservice;

import org.junit.jupiter.api.Test;
import org.osanzana.userservice.model.Phone;
import org.osanzana.userservice.model.User;
import org.osanzana.userservice.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testSaveUserAndFindByEmail() {
        User user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setPassword(passwordEncoder.encode("Password12345"));
        user.setPhones(Collections.emptyList());

        User savedUser = userRepository.save(user);

        assertNotNull(savedUser.getId());
        assertEquals("Test User", savedUser.getName());
        assertEquals("test@example.com", savedUser.getEmail());
        assertTrue(passwordEncoder.matches("Password12345", savedUser.getPassword()));
        assertNotNull(savedUser.getCreated());
        assertNotNull(savedUser.getModified());
        assertNotNull(savedUser.getLastLogin());
        assertTrue(savedUser.isActive());

        Optional<User> foundUser = userRepository.findByEmail("test@example.com");
        assertTrue(foundUser.isPresent());
        assertEquals(savedUser.getId(), foundUser.get().getId());
        assertEquals(savedUser.getEmail(), foundUser.get().getEmail());
    }

    @Test
    void testFindByEmail_NotFound() {
        Optional<User> foundUser = userRepository.findByEmail("nonexistent@example.com");
        assertFalse(foundUser.isPresent());
    }

    @Test
    void testSaveUserWithPhones() {
        User user = new User();
        user.setName("User With Phones");
        user.setEmail("user.phones@example.com");
        user.setPassword(passwordEncoder.encode("SecurePassword123"));

        Phone phone1 = new Phone();
        phone1.setNumber("1111111111");
        phone1.setCityCode("1");
        phone1.setCountryCode("56");

        Phone phone2 = new Phone();
        phone2.setNumber("2222222222");
        phone2.setCityCode("2");
        phone2.setCountryCode("56");

        user.setPhones(List.of(phone1, phone2));

        User savedUser = userRepository.save(user);

        assertNotNull(savedUser.getId());
        assertEquals(2, savedUser.getPhones().size());
        assertEquals("1111111111", savedUser.getPhones().get(0).getNumber());
        assertEquals("2222222222", savedUser.getPhones().get(1).getNumber());
        assertNotNull(savedUser.getPhones().get(0).getId());
    }
}
