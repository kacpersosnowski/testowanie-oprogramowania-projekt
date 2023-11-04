package com.testowanieoprogramowaniaprojekt.controllers;

import com.testowanieoprogramowaniaprojekt.entities.User;
import com.testowanieoprogramowaniaprojekt.exceptions.BadRequestException;
import com.testowanieoprogramowaniaprojekt.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handleException(BadRequestException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    ResponseEntity<List<User>> findAll() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<User> create(@RequestBody User user) {
        validateUser(user);
        return new ResponseEntity<>(userService.save(user), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<User> findById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user) {
        validateUser(user);
        return new ResponseEntity<>(userService.update(id, user), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void validateUser(User user) {
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new BadRequestException("Username is mandatory.");
        } else if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            throw new BadRequestException("Password is mandatory.");
        }
    }
}
