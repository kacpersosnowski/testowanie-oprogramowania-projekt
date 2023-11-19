package com.testowanieoprogramowaniaprojekt.services;

import com.testowanieoprogramowaniaprojekt.entities.Post;
import com.testowanieoprogramowaniaprojekt.exceptions.BadRequestException;
import com.testowanieoprogramowaniaprojekt.repositories.PostRepository;
import com.testowanieoprogramowaniaprojekt.repositories.SubredditRepository;
import com.testowanieoprogramowaniaprojekt.repositories.UserRepository;
import com.testowanieoprogramowaniaprojekt.testData.TestDataBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class PostServiceTests {

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SubredditRepository subredditRepository;

    @InjectMocks
    private PostService postService;

    @Test
    void test_findAllPosts_shouldReturnPostList() {
        // given
        List<Post> posts = new ArrayList<>(Arrays.asList(
                TestDataBuilder.examplePost().post(),
                TestDataBuilder.examplePost2().post(),
                TestDataBuilder.examplePost3().post()
        ));

        // when
        when(postRepository.findAll()).thenReturn(posts);
        List<Post> result = postRepository.findAll();

        // then
        assertArrayEquals(posts.toArray(), result.toArray());
    }

    @Test
    void test_findById_shouldReturnPost() {
        // given
        Long searchedPostId = 1L;
        Post post = TestDataBuilder.examplePost().post();

        // when
        when(postRepository.findById(searchedPostId)).thenReturn(Optional.of(post));
        Optional<Post> result = Optional.ofNullable(postService.findById(searchedPostId));

        // then
        assertTrue(result.isPresent());
        assertEquals(post, result.get());
    }

    @Test
    void test_findById_shouldThrowResponseStatusException() {
        // given
        Long searchedPostId = 2L;

        // when
        when(postRepository.findById(searchedPostId))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found."));

        // then
        assertThrows(ResponseStatusException.class, () -> postService.findById(searchedPostId));
    }

    @Test
    void test_deleteById_shouldDeletePostFromDatabase() {
        // given
        Long postId = 1L;

        // when
        postService.deleteById(postId);

        // then
        verify(postRepository).deleteById(postId);
    }

    @Test
    void test_save_shouldReturnAddedPost() {
        // given
        Post post = TestDataBuilder.examplePost().post();

        // when
        when(userRepository.findById(post.getAuthor().getId()))
                .thenReturn(Optional.ofNullable(post.getAuthor()));
        when(subredditRepository.findById(post.getSubreddit().getId()))
                .thenReturn(Optional.ofNullable(post.getSubreddit()));
        when(postRepository.save(post)).thenReturn(post);
        Post result = postService.save(post);

        // then
        assertEquals(post, result);
    }

    @Test
    void test_save_shouldThrowResponseStatusExceptionForUser() {
        // given
        Post post = TestDataBuilder.examplePost().post();

        // when
        when(userRepository.findById(post.getAuthor().getId()))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));

        // then
        assertThrows(ResponseStatusException.class, () -> postService.save(post));
    }

    @Test
    void test_save_shouldThrowResponseStatusExceptionForSubreddit() {
        // given
        Post post = TestDataBuilder.examplePost().post();

        // when
        when(userRepository.findById(post.getAuthor().getId()))
                .thenReturn(Optional.ofNullable(post.getAuthor()));
        when(subredditRepository.findById(post.getSubreddit().getId()))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Subreddit not found."));

        // then
        assertThrows(ResponseStatusException.class, () -> postService.save(post));
    }

    @ParameterizedTest
    @MethodSource("provideIncorrectPostList")
    void test_save_shouldThrowBadRequestException(Post post) {
        assertThrows(BadRequestException.class, () -> postService.save(post));
    }

    @Test
    void test_update_shouldReturnUpdatedPost() {
        // given
        Post postToUpdate = TestDataBuilder.examplePost().post();
        Post updatedPost = TestDataBuilder.examplePost2().post();

        // when
        when(postRepository.findById(postToUpdate.getId()))
                .thenReturn(Optional.of(postToUpdate));
        when(postRepository.save(postToUpdate)).thenReturn(updatedPost);
        Post result = postService.update(postToUpdate.getId(), updatedPost);

        // then
        assertEquals(updatedPost, result);
    }

    @Test
    void test_update_shouldThrowResponseStatusException() {
        Long postId = -1L;
        Post post = TestDataBuilder.examplePost().post();

        assertThrows(ResponseStatusException.class, () -> postService.update(postId, post));
    }

    @ParameterizedTest
    @MethodSource("provideIncorrectPostList")
    void test_update_shouldThrowBadRequestException(Post post) {
        Long postId = 1L;
        assertThrows(BadRequestException.class, () -> postService.update(postId, post));
    }

    private static Stream<Post> provideIncorrectPostList() {
        return TestDataBuilder
                .invalidPostList()
                .invalidPosts();
    }
}
