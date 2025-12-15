package ru.olympinfo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.olympinfo.model.Role;
import ru.olympinfo.model.User;
import ru.olympinfo.repository.UserRepository;

import java.util.HashSet;

@SpringBootApplication
public class OlympInfoApplication {
    public static void main(String[] args) {
        SpringApplication.run(OlympInfoApplication.class, args);
    }

    @Bean
    CommandLineRunner initUsers(UserRepository repo, PasswordEncoder encoder) {
        return args -> {
            if (repo.findByUsername("admin").isEmpty()) {
                repo.save(new User(
                        null,
                        "admin",
                        encoder.encode("admin"),
                        Role.ADMIN,
                        new HashSet<>()
                ));
            }

            if (repo.findByUsername("user").isEmpty()) {
                repo.save(new User(
                        null,
                        "user",
                        encoder.encode("user"),
                        Role.USER,
                        new HashSet<>()
                ));
            }

            if (repo.findByUsername("organizer").isEmpty()) {
                repo.save(new User(
                        null,
                        "organizer",
                        encoder.encode("organizer"),
                        Role.ORGANIZER,
                        new HashSet<>()
                ));
            }
        };
    }
}
