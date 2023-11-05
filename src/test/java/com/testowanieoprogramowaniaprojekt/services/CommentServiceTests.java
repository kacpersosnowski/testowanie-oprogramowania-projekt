package com.testowanieoprogramowaniaprojekt.services;

import com.testowanieoprogramowaniaprojekt.entities.Comment;
import com.testowanieoprogramowaniaprojekt.exceptions.BadRequestException;
import com.testowanieoprogramowaniaprojekt.repositories.CommentRepository;
import com.testowanieoprogramowaniaprojekt.repositories.PostRepository;
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

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class CommentServiceTests {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private CommentService commentService;

    @Test
    void test_GetAllComments_Success() {
        // given
        List<Comment> comments = new ArrayList<>(Arrays.asList(
                TestDataBuilder.exampleComment().comment(),
                TestDataBuilder.exampleComment2().comment(),
                TestDataBuilder.exampleComment3().comment()));

        // when
        Mockito.when(commentRepository.findAll()).thenReturn(comments);
        List<Comment> result = commentService.findAll();

        // then
        assertArrayEquals(comments.toArray(), result.toArray());
    }

    @Test
    void test_GetCommentById_Success() {
        // given
        Comment comment = TestDataBuilder.exampleComment().comment();
        Long commentId = 1L;

        // when
        Mockito.when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));
        Optional<Comment> result = Optional.ofNullable(commentService.findById(commentId));

        // then
        assertTrue(result.isPresent());
        assertEquals(comment, result.get());
    }

    @Test
    void test_GetCommentById_Failure() {
        // given
        Long commentId = -1L;

        // when
        Mockito.when(commentRepository
                .findById(commentId))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found."));

        // then
        assertThrows(ResponseStatusException.class, () -> commentService.findById(commentId));
    }

    @Test
    void test_CreateComment_Success() {
        // given
        Comment commentToBeCreated = TestDataBuilder.exampleComment().comment();

        // when
        Mockito.when(userRepository.findById(commentToBeCreated.getAuthor().getId()))
                .thenReturn(Optional.ofNullable(commentToBeCreated.getAuthor()));
        Mockito.when(postRepository.findById(commentToBeCreated.getPost().getId()))
                .thenReturn(Optional.ofNullable(commentToBeCreated.getPost()));
        Mockito.when(commentRepository.save(commentToBeCreated)).thenReturn(commentToBeCreated);
        Comment result = commentService.save(commentToBeCreated);

        // then
        assertEquals(commentToBeCreated.getAuthor(), result.getAuthor());
        assertEquals(commentToBeCreated.getPost(), result.getPost());
    }

    @ParameterizedTest
    @MethodSource("provideInvalidComments")
    void test_CreateComment_FailureInvalidRequestData(Comment invalidComment) {
        assertThrows(BadRequestException.class, () -> commentService.save(invalidComment));
    }

    private static Stream<Comment> provideInvalidComments() {
        return TestDataBuilder.invalidCommentsList().invalidComments();
    }

    @Test
    void test_CreateComment_FailureUserNotFound() {
        // given
        Comment commentToBeCreated = TestDataBuilder.exampleComment().comment();

        // when
        Mockito.when(userRepository.findById(commentToBeCreated.getAuthor().getId()))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        // then
        assertThrows(ResponseStatusException.class, () -> commentService.save(commentToBeCreated));
    }

    @Test
    void test_CreateComment_FailurePostNotFound() {
        // given
        Comment commentToBeCreated = TestDataBuilder.exampleComment().comment();

        // when
        Mockito.when(postRepository.findById(commentToBeCreated.getPost().getId()))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));

        // then
        assertThrows(ResponseStatusException.class, () -> commentService.save(commentToBeCreated));
    }

    @Test
    void test_UpdateComment_Success() {
        // given
        Comment existingComment = TestDataBuilder.exampleComment().comment();
        Comment updatedComment = TestDataBuilder.exampleComment2().comment();

        // when
        Mockito.when(commentRepository.findById(existingComment.getId()))
                .thenReturn(Optional.of(existingComment));
        Mockito.when(commentRepository.save(existingComment))
                .thenReturn(updatedComment);
        Comment result = commentService.update(existingComment.getId(), updatedComment);

        // then
        verify(commentRepository, times(1)).findById(existingComment.getId());
        verify(commentRepository, times(1)).save(existingComment);
        assertEquals(updatedComment.getContent(), result.getContent());
    }

    @ParameterizedTest
    @MethodSource("provideInvalidComments")
    void test_UpdateComment_FailureInvalidRequestData(Comment invalidComment) {
        Comment existingComment = TestDataBuilder.exampleComment().comment();

        assertThrows(BadRequestException.class, () -> commentService.update(existingComment.getId(), invalidComment));
    }

    @Test
    void test_UpdateComment_FailureCommentNotFound() {
        // given
        Comment updatedComment = TestDataBuilder.exampleComment2().comment();
        Long invalidId = -1L;

        // when
        Mockito.when(commentRepository.findById(invalidId))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found"));

        // then
        assertThrows(ResponseStatusException.class, () -> commentService.update(invalidId, updatedComment));
    }

    @Test
    void test_DeleteComment_Success() {
        // given
        Comment comment = TestDataBuilder.exampleComment().comment();

        // when
        Mockito.doNothing().when(commentRepository).deleteById(comment.getId());
        commentService.deleteById(comment.getId());

        // then
        verify(commentRepository, times(1)).deleteById(comment.getId());
    }
}
