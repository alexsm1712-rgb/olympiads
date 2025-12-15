package ru.olympinfo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.olympinfo.model.Role;
import ru.olympinfo.model.User;
import ru.olympinfo.repository.UserRepository;
import java.util.HashSet;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            userRepository.save(new User(null,"user", passwordEncoder.encode("1"), Role.USER,new HashSet<>()));
            userRepository.save(new User(null,"organizer", passwordEncoder.encode("1"), Role.ORGANIZER,new HashSet<>()));
            userRepository.save(new User(null,"admin", passwordEncoder.encode("1"), Role.ADMIN,new HashSet<>()));
        }
    }
}