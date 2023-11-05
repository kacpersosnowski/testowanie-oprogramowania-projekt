package com.testowanieoprogramowaniaprojekt.services;

import com.testowanieoprogramowaniaprojekt.entities.Post;
import com.testowanieoprogramowaniaprojekt.exceptions.BadRequestException;
import com.testowanieoprogramowaniaprojekt.repositories.PostRepository;
import com.testowanieoprogramowaniaprojekt.testData.TestDataBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class PostServiceTests {

    @Mock
    private PostRepository postRepository;

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
    void test_findById_shouldReturnError() {
        // given
        Long searchedPostId = 2L;

        // when
        when(postRepository.findById(searchedPostId))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found."));

        // then
        assertThrows(ResponseStatusException.class, () -> postService.findById(searchedPostId));
    }

    @Test
    void test_findById_shouldThrowBadRequestException() {
        // given
        Long searchedPostId = -1L;

        // when
        when(postRepository.findById(searchedPostId))
                .thenThrow(new BadRequestException("Post Id can't be less than 0."));

        // then
        assertThrows(BadRequestException.class, () -> postService.findById(searchedPostId));
    }
}
