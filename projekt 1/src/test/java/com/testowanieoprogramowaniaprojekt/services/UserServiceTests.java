package com.testowanieoprogramowaniaprojekt.services;

import com.testowanieoprogramowaniaprojekt.entities.User;
import com.testowanieoprogramowaniaprojekt.exceptions.BadRequestException;
import com.testowanieoprogramowaniaprojekt.repositories.UserRepository;
import com.testowanieoprogramowaniaprojekt.testData.TestDataBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void test_GetAllUsers_Success() {
        // given
        List<User> users = TestDataBuilder.exampleUserList(3).userList();

        // when
        Mockito.when(userRepository.findAll()).thenReturn(users);
        List<User> result = userService.findAll();

        // then
        assertArrayEquals(users.toArray(), result.toArray());
    }

    @Test
    void test_GetUserById_Success() {
        // given
        User user = TestDataBuilder.exampleUser().user();
        Long userId = 1L;

        // when
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Optional<User> result = Optional.ofNullable(userService.findById(userId));

        // then
        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    void test_GetUserById_Failure() {
        // given
        Long userId = -1L;

        // when
        Mockito.when(userRepository.findById(userId))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));

        // then
        assertThrows(ResponseStatusException.class, () -> userService.findById(userId));
    }

    @Test
    void test_CreateUser_Success() {
        // given
        User userToBeCreated = TestDataBuilder.exampleUser().user();

        // when
        Mockito.when(userRepository.save(userToBeCreated)).thenReturn(userToBeCreated);
        User result = userService.save(userToBeCreated);

        // then
        assertEquals(userToBeCreated.getUsername(), result.getUsername());
        assertEquals(userToBeCreated.getPassword(), result.getPassword());
    }

    @ParameterizedTest
    @MethodSource("provideInvalidUsers")
    void test_CreateUser_FailureInvalidRequestData(User invalidUser) {
        assertThrows(BadRequestException.class, () -> userService.save(invalidUser));
    }

    private static List<User> provideInvalidUsers() {
        return TestDataBuilder.invalidUserList().invalidUserList();
    }

    @Test
    void test_UpdateUser_Success() {
        // given
        User existingUser = TestDataBuilder.exampleUser().user();
        User updatedUser = TestDataBuilder.exampleUser().user();
        updatedUser.setUsername("updatedUsername");

        // when
        Mockito.when(userRepository.findById(existingUser.getId()))
                .thenReturn(Optional.of(existingUser));
        Mockito.when(userRepository.save(existingUser))
                .thenReturn(updatedUser);
        User result = userService.update(existingUser.getId(), updatedUser);

        // then
        verify(userRepository, times(1)).findById(existingUser.getId());
        verify(userRepository, times(1)).save(existingUser);
        assertEquals(updatedUser.getUsername(), result.getUsername());
    }

    @ParameterizedTest
    @MethodSource("provideInvalidUsers")
    void test_UpdateUser_FailureInvalidRequestData(User invalidUser) {
        User existingUser = TestDataBuilder.exampleUser().user();
        Long userId = existingUser.getId();

        assertThrows(BadRequestException.class, () -> userService.update(userId, invalidUser));
    }

    @Test
    void test_DeleteUser_Success() {
        // given
        User user = TestDataBuilder.exampleUser().user();

        // when
        Mockito.doNothing().when(userRepository).deleteById(user.getId());
        userService.deleteById(user.getId());

        // then
        verify(userRepository, times(1)).deleteById(user.getId());
    }
}
