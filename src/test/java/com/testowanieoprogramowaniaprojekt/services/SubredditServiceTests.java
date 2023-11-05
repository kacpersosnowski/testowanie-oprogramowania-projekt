package com.testowanieoprogramowaniaprojekt.services;

import com.testowanieoprogramowaniaprojekt.entities.Subreddit;
import com.testowanieoprogramowaniaprojekt.exceptions.BadRequestException;
import com.testowanieoprogramowaniaprojekt.repositories.SubredditRepository;
import com.testowanieoprogramowaniaprojekt.repositories.UserRepository;
import com.testowanieoprogramowaniaprojekt.testData.TestDataBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class SubredditServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private SubredditRepository subredditRepository;

    @InjectMocks
    private SubredditService subredditService;

    @Test
    public void test_GetAllSubreddits_Success() {
        // given
        List<Subreddit> subreddits = new ArrayList<>(Arrays.asList(
                TestDataBuilder.exampleSubreddit().subreddit(),
                TestDataBuilder.exampleSubreddit2().subreddit(),
                TestDataBuilder.exampleSubreddit3().subreddit()));

        // when
        Mockito.when(subredditRepository.findAll()).thenReturn(subreddits);
        List<Subreddit> result = subredditService.getAllSubreddits();

        // then
        assertArrayEquals(subreddits.toArray(), result.toArray());
    }

    @Test
    public void test_GetSubredditById_Success() {
        // given
        Subreddit subreddit = TestDataBuilder.exampleSubreddit().subreddit();
        Long subredditId = 1L;

        // when
        Mockito.when(subredditRepository.findById(subredditId)).thenReturn(Optional.of(subreddit));
        Optional<Subreddit> result = Optional.ofNullable(subredditService.findById(subredditId));

        // then
        assertTrue(result.isPresent());
        assertEquals(subreddit, result.get());
    }

    @Test
    public void test_GetSubredditById_Failure() {
        // given
        Long subredditId = -1L;

        // when
        Mockito.when(subredditRepository.findById(subredditId))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Subreddit not found."));

        // then
        assertThrows(ResponseStatusException.class, () -> subredditService.findById(subredditId));
    }

    @Test
    public void test_CreateSubreddit_Success() {
        // given
        Subreddit subredditToBeCreated = TestDataBuilder.exampleSubreddit().subreddit();
        // when
        Mockito.when(userRepository.findById(subredditToBeCreated.getUser().getId()))
                .thenReturn(Optional.ofNullable(subredditToBeCreated.getUser()));
        Mockito.when(subredditRepository.save(subredditToBeCreated)).thenReturn(subredditToBeCreated);
        Subreddit result = subredditService.createSubreddit(subredditToBeCreated);

        // then
        assertEquals(subredditToBeCreated.getUser(), result.getUser());
    }


    @ParameterizedTest
    @MethodSource("provideInvalidSubreddits")
    public void test_CreateSubreddit_FailureInvalidRequestData(Subreddit invalidSubreddit) {
        assertThrows(BadRequestException.class, () -> subredditService.createSubreddit(invalidSubreddit));
    }

    private static Stream<Subreddit> provideInvalidSubreddits() {
        return TestDataBuilder.invalidSubredditsList().invalidSubreddits();
    }

    @Test
    public void test_CreateSubreddit_FailureUserNotFound() {
        // given
        Subreddit subredditToBeCreated = TestDataBuilder.exampleSubreddit().subreddit();

        // when
        Mockito.when(userRepository.findById(subredditToBeCreated.getUser().getId()))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        // then
        assertThrows(ResponseStatusException.class, () -> subredditService.createSubreddit(subredditToBeCreated));
    }

    @Test
    public void test_UpdateSubreddit_Success() {
        // given
        Subreddit existingSubreddit = TestDataBuilder.exampleSubreddit().subreddit();
        Subreddit updatedSubreddit = TestDataBuilder.exampleSubreddit2().subreddit();

        // when
        Mockito.when(subredditRepository.findById(existingSubreddit.getId()))
                .thenReturn(Optional.of(existingSubreddit));
        Mockito.when(subredditRepository.save(existingSubreddit))
                .thenReturn(updatedSubreddit);
        Subreddit result = subredditService.updateSubreddit(existingSubreddit.getId(), updatedSubreddit);

        // then
        verify(subredditRepository, times(1)).findById(existingSubreddit.getId());
        verify(subredditRepository, times(1)).save(existingSubreddit);
        assertEquals(updatedSubreddit.getName(), result.getName());
    }

    @ParameterizedTest
    @MethodSource("provideInvalidSubreddits")
    public void test_UpdateSubreddit_FailureInvalidRequestData(Subreddit invalidSubreddit) {
        Subreddit existingSubreddit = TestDataBuilder.exampleSubreddit().subreddit();

        assertThrows(BadRequestException.class, () -> subredditService.updateSubreddit(existingSubreddit.getId(), invalidSubreddit));
    }

    @Test
    public void test_UpdateSubreddit_FailureSubredditNotFound() {
        // given
        Subreddit updatedSubreddit = TestDataBuilder.exampleSubreddit2().subreddit();
        Long invalidId = -1L;

        // when
        Mockito.when(subredditRepository.findById(invalidId))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Subreddit not found"));

        // then
        assertThrows(ResponseStatusException.class, () -> subredditService.updateSubreddit(invalidId, updatedSubreddit));
    }

    @Test
    public void test_DeleteSubreddit_Success() {
        // given
        Subreddit subreddit = TestDataBuilder.exampleSubreddit().subreddit();

        // when
        Mockito.doNothing().when(subredditRepository).deleteById(subreddit.getId());
        subredditService.deleteSubreddit(subreddit.getId());

        // then
        verify(subredditRepository, times(1)).deleteById(subreddit.getId());
    }
}
