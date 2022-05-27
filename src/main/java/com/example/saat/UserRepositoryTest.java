package com.example.saat;

import com.example.saat.models.User;
import com.example.saat.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Test
    public void testCreateUser() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String rawPassword = "Darina";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        User newUser = new User("testUser", encodedPassword);
        User savedUser = userRepository.save(newUser);
    }
}
