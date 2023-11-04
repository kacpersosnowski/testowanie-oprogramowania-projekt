package com.testowanieoprogramowaniaprojekt.services;

import com.testowanieoprogramowaniaprojekt.entities.User;
import com.testowanieoprogramowaniaprojekt.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User save(User user) {
        user.setPassword(hashPassword(user.getPassword()));
        return userRepository.save(user);
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));
    }

    public User update(Long id, User user) {
        user.setPassword(hashPassword(user.getPassword()));
        return userRepository.findById(id)
                .map(currentUser -> {
                    currentUser.setUsername(user.getUsername());
                    currentUser.setPassword(user.getPassword());
                    return userRepository.save(currentUser);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(10));
    }
}
