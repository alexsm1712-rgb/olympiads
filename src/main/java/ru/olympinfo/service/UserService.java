package ru.olympinfo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.olympinfo.model.User;
import ru.olympinfo.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
}
