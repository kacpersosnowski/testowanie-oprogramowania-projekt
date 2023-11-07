package com.testowanieoprogramowaniaprojekt.controllers;

import com.testowanieoprogramowaniaprojekt.entities.User;
import com.testowanieoprogramowaniaprojekt.exceptions.BadRequestException;
import com.testowanieoprogramowaniaprojekt.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "users")
@Tag(name = "Users", description = "Users management APIs")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handleException(BadRequestException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @Operation(
            summary = "Find all Users",
            description = "Get all User instances."
    )
    @GetMapping
    ResponseEntity<List<User>> findAll() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @Operation(
            summary = "Post a User",
            description = "Post a User to database."
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<User> create(@RequestBody User user) {
        validateUser(user);
        return new ResponseEntity<>(userService.save(user), HttpStatus.OK);
    }

    @Operation(
            summary = "Find User by id",
            description = "Get a User by specifying their id."
    )
    @ApiResponse(
            responseCode = "200",
            content = {
                    @Content(schema = @Schema(implementation = User.class), mediaType = "application/json")})
    @ApiResponse(
            responseCode = "404",
            content = {@Content(schema = @Schema())})
    @ApiResponse(
            responseCode = "500",
            content = {@Content(schema = @Schema())})
    @GetMapping("/{id}")
    ResponseEntity<User> findById(
            @Parameter(description = "User id.", example = "1")
            @PathVariable Long id
    ) {
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }

    @Operation(
            summary = "Update User",
            description = "Put User by specifying their id and providing new User."
    )
    @PutMapping("/{id}")
    ResponseEntity<User> update(
            @Parameter(description = "User id.", example = "1")
            @PathVariable Long id,
            @RequestBody User user
    ) {
        validateUser(user);
        return new ResponseEntity<>(userService.update(id, user), HttpStatus.OK);
    }

    @Operation(
            summary = "Delete User by id",
            description = "Delete a User by specifying their id."
    )
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(
            @Parameter(description = "User id.", example = "1")
            @PathVariable Long id
    ) {
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
