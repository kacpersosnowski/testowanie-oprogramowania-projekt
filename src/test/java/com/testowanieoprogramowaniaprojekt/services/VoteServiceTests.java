package com.testowanieoprogramowaniaprojekt.services;

import com.testowanieoprogramowaniaprojekt.entities.Vote;
import com.testowanieoprogramowaniaprojekt.exceptions.BadRequestException;
import com.testowanieoprogramowaniaprojekt.repositories.CommentRepository;
import com.testowanieoprogramowaniaprojekt.repositories.PostRepository;
import com.testowanieoprogramowaniaprojekt.repositories.UserRepository;
import com.testowanieoprogramowaniaprojekt.repositories.VoteRepository;
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

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class VoteServiceTests {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private VoteRepository voteRepository;

    @InjectMocks
    private VoteService voteService;

    @Test
    void test_CreatePostVote_Success() {
        // given
        Vote newVote = TestDataBuilder.examplePostVote().vote();

        // when
        Mockito.when(userRepository.findById(newVote.getUser().getId()))
                .thenReturn(Optional.ofNullable(newVote.getUser()));
        Mockito.when(postRepository.findById(newVote.getPost().getId()))
                .thenReturn(Optional.ofNullable(newVote.getPost()));
        Mockito.when(voteRepository.save(newVote)).thenReturn(newVote);
        Vote result = voteService.save(newVote);

        // then
        assertEquals(newVote.getUser(), result.getUser());
        assertEquals(newVote.getPost(), result.getPost());
    }

    @Test
    void test_CreateCommentVote_Success() {
        // given
        Vote newVote = TestDataBuilder.exampleCommentVote().vote();

        // when
        Mockito.when(userRepository.findById(newVote.getUser().getId()))
                .thenReturn(Optional.ofNullable(newVote.getUser()));
        Mockito.when(commentRepository.findById(newVote.getComment().getId()))
                .thenReturn(Optional.ofNullable(newVote.getComment()));
        Mockito.when(voteRepository.save(newVote)).thenReturn(newVote);
        Vote result = voteService.save(newVote);

        // then
        assertEquals(newVote.getUser(), result.getUser());
        assertEquals(newVote.getComment(), result.getComment());
    }

    @Test
    void test_CreateVote_FailurePostNotFound() {
        // given
        Vote newVote = TestDataBuilder.examplePostVote().vote();

        // when
        Mockito.when(voteRepository.findById(newVote.getPost().getId()))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));

        // then
        assertThrows(ResponseStatusException.class, () -> voteService.save(newVote));
    }

    @Test
    void test_CreateVote_FailureCommentNotFound() {
        // given
        Vote newVote = TestDataBuilder.exampleCommentVote().vote();

        // when
        Mockito.when(voteRepository.findById(newVote.getComment().getId()))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found"));

        // then
        assertThrows(ResponseStatusException.class, () -> voteService.save(newVote));
    }

    @ParameterizedTest
    @MethodSource("provideInvalidVoteList")
    void test_CreateVote_FailureInvalidRequestData(Vote invalidVote) {
        assertThrows(BadRequestException.class, () -> voteService.save(invalidVote));
    }

    @Test
    void test_GetVoteById_Success() {
        // given
        Vote vote = TestDataBuilder.examplePostVote().vote();
        Long voteId = 1L;

        // when
        Mockito.when(voteRepository.findById(voteId)).thenReturn(Optional.of(vote));
        Optional<Vote> result = Optional.ofNullable(voteService.findById(voteId));

        // then
        assertTrue(result.isPresent());
        assertEquals(vote, result.get());
    }

    @Test
    void test_GetVoteById_Failure() {
        // given
        Long voteId = 2L;

        // when
        Mockito.when(voteRepository
                .findById(voteId))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Vote not found."));

        // then
        assertThrows(ResponseStatusException.class, () -> voteService.findById(voteId));
    }
    
    @Test
    void test_GetAllVotes_Success() {
        // given
        List<Vote> votes = new ArrayList<>(Arrays.asList(
                TestDataBuilder.examplePostVote().vote(),
                TestDataBuilder.exampleCommentVote().vote()));

        // when
        Mockito.when(voteRepository.findAll()).thenReturn(votes);
        List<Vote> result = voteService.findAll();

        // then
        assertArrayEquals(votes.toArray(), result.toArray());
    }

    @Test
    void test_UpdateVote_Success() {
        // given
        Vote existingVote = TestDataBuilder.examplePostVote().vote();
        Vote updatedVote = TestDataBuilder.exampleCommentVote().vote();

        // when
        Mockito.when(voteRepository.findById(existingVote.getId()))
                .thenReturn(Optional.of(existingVote));
        Mockito.when(voteRepository.save(existingVote))
                .thenReturn(updatedVote);
        Vote result = voteService.update(existingVote.getId(), updatedVote);

        // then
        verify(voteRepository, times(1)).findById(existingVote.getId());
        verify(voteRepository, times(1)).save(existingVote);
        assertEquals(updatedVote.getId(), result.getId());
    }

    @Test
    void test_UpdateVote_FailureVoteNotFound() {
        // given
        Vote newVote = TestDataBuilder.examplePostVote().vote();
        Long invalidId = -1L;

        // when
        Mockito.when(voteRepository.findById(invalidId))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Vote not found"));

        // then
        assertThrows(ResponseStatusException.class, () -> voteService.update(invalidId, newVote));
    }

    @Test
    void test_deleteById_Success() {
        // given
        Long voteId = 1L;

        // when
        voteService.deleteVote(voteId);

        // then
        verify(voteRepository).deleteById(voteId);
    }

    private static Stream<Vote> provideInvalidVoteList() {
        return TestDataBuilder
            .invalidVoteList()
            .invalidVoteList();
            
    }
}