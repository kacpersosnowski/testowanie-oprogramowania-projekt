package com.testowanieoprogramowaniaprojekt.controllers;

import com.testowanieoprogramowaniaprojekt.entities.Comment;
import com.testowanieoprogramowaniaprojekt.exceptions.BadRequestException;
import com.testowanieoprogramowaniaprojekt.services.CommentService;
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

import java.util.List;

@RestController
@RequestMapping(value = "comments")
@Tag(name = "Comments", description = "Comments management APIs")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handleException(BadRequestException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @Operation(
            summary = "Find all Comments",
            description = "Get all Comment instances."
    )
    @GetMapping
    public ResponseEntity<List<Comment>> getAllComments() {
        return new ResponseEntity<>(commentService.findAll(), HttpStatus.OK);
    }

    @Operation(
            summary = "Find Comment by id",
            description = "Get a Comment by specifying its id."
    )
    @ApiResponse(
            responseCode = "200",
            content = {
                    @Content(schema = @Schema(implementation = Comment.class), mediaType = "application/json")})
    @ApiResponse(
            responseCode = "404",
            content = {@Content(schema = @Schema())})
    @ApiResponse(
            responseCode = "500",
            content = {@Content(schema = @Schema())})
    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById(
            @Parameter(description = "Comment id.", example = "1")
            @PathVariable("id") long id
    ) {
        return new ResponseEntity<>(commentService.findById(id), HttpStatus.OK);
    }

    @Operation(
            summary = "Post a Comment",
            description = "Post a Comment to database."
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment) {
        return new ResponseEntity<>(commentService.save(comment), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update Comment",
            description = "Put a Comment by specifying its id and providing new Comment."
    )
    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(
            @Parameter(description = "Comment id.", example = "1")
            @PathVariable("id") long id,
            @RequestBody Comment comment
    ) {
        return new ResponseEntity<>(commentService.update(id, comment), HttpStatus.OK);
    }

    @Operation(
            summary = "Delete Comment by id",
            description = "Delete a Comment by specifying its id."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteComment(
            @Parameter(description = "Comment id.", example = "1")
            @PathVariable Long id
    ) {
        commentService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "Get votes for comment",
            description = "Get votes for comment by specifying their id."
    )
    @GetMapping("/{id}/votes")
    int getVotes(
            @Parameter(description = "Vote id.", example = "1")
            @PathVariable Long id
    ) {
        return commentService.getVotes(id);
    }
}
