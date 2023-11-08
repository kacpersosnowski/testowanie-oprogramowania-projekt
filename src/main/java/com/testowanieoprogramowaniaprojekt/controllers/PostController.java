package com.testowanieoprogramowaniaprojekt.controllers;

import com.testowanieoprogramowaniaprojekt.entities.Post;
import com.testowanieoprogramowaniaprojekt.exceptions.BadRequestException;
import com.testowanieoprogramowaniaprojekt.services.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("posts")
@Tag(name = "Post", description = "Posts management APIs")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handleException(BadRequestException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @Operation(
            summary = "Find all Posts",
            description = "Get all Post instances."
    )
    @GetMapping
    Collection<Post> findAll() {
        return postService.findAll();
    }

    @Operation(
            summary = "Find Post by id",
            description = "Get a Post by specifying its id."
    )
    @ApiResponse(
            responseCode = "200",
            content = {
                    @Content(schema = @Schema(implementation = Post.class), mediaType = "application/json")})
    @ApiResponse(
            responseCode = "404",
            content = {@Content(schema = @Schema())})
    @ApiResponse(
            responseCode = "500",
            content = {@Content(schema = @Schema())})
    @GetMapping("/{id}")
    Post findById(
            @Parameter(description = "Post id.", example = "1")
            @PathVariable Long id
    ) {
        return postService.findById(id);
    }

    @Operation(
            summary = "Post a Post",
            description = "Post a Post to database."
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    Post create(@RequestBody Post post) {
        return postService.save(post);
    }

    @Operation(
            summary = "Update Post",
            description = "Put a Post by specifying its id and providing new Post."
    )
    @PutMapping("/{id}")
    Post update(
            @RequestBody Post post,
            @Parameter(description = "Post id.", example = "1")
            @PathVariable Long id
    ) {
        return postService.update(id, post);
    }

    @Operation(
            summary = "Delete Post by id",
            description = "Delete a Post by specifying its id."
    )
    @DeleteMapping("/{id}")
    void delete(
            @Parameter(description = "Post id.", example = "1")
            @PathVariable Long id
    ) {
        postService.deleteById(id);
    }

    @Operation(
            summary = "Get votes for post",
            description = "Get votes for post by specifying their id."
    )
    @GetMapping("/{id}/votes")
    int getVotes(
            @Parameter(description = "Vote id.", example = "1")
            @PathVariable Long id
    ) {
        return postService.getVotes(id);
    }
}
