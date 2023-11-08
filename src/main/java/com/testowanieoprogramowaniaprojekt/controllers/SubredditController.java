package com.testowanieoprogramowaniaprojekt.controllers;

import com.testowanieoprogramowaniaprojekt.entities.Subreddit;
import com.testowanieoprogramowaniaprojekt.exceptions.BadRequestException;
import com.testowanieoprogramowaniaprojekt.services.SubredditService;
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
@RequestMapping("/subreddits")
@Tag(name = "Subreddits", description = "Subreddits management APIs")
@RequiredArgsConstructor
public class SubredditController {
    private final SubredditService subredditService;

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handleException(BadRequestException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @Operation(
            summary = "Post a Subreddit",
            description = "Post a Subreddit to database."
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Subreddit createSubreddit(@RequestBody Subreddit subreddit) {
        return subredditService.createSubreddit(subreddit);
    }

    @Operation(
            summary = "Find all Subreddits",
            description = "Get all Subreddit instances."
    )
    @GetMapping
    public List<Subreddit> getAllSubreddits() {
        return subredditService.getAllSubreddits();
    }

    @Operation(
            summary = "Find Subreddit by id",
            description = "Get a Subreddit by specifying its id."
    )
    @ApiResponse(
            responseCode = "200",
            content = {
                    @Content(schema = @Schema(implementation = Subreddit.class), mediaType = "application/json")})
    @ApiResponse(
            responseCode = "404",
            content = {@Content(schema = @Schema())})
    @ApiResponse(
            responseCode = "500",
            content = {@Content(schema = @Schema())})
    @GetMapping("/{id}")
    public Subreddit getSubredditById(
            @Parameter(description = "Subreddit id.", example = "1")
            @PathVariable Long id
    ) {
        return subredditService.findById(id);
    }

    @Operation(
            summary = "Update Subreddit ",
            description = "Put a Subreddit by specifying its id and providing new Subreddit."
    )
    @PutMapping("/{id}")
    public Subreddit updateSubreddit(
            @Parameter(description = "Subreddit id.", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody Subreddit updatedSubreddit
    ) {
        return subredditService.updateSubreddit(id, updatedSubreddit);
    }

    @Operation(
            summary = "Delete Subreddit by id",
            description = "Delete a Subreddit by specifying its id."
    )
    @DeleteMapping("/{id}")
    public void deleteSubreddit(
            @Parameter(description = "Subreddit id.", example = "1")
            @PathVariable Long id
    ) {
        subredditService.deleteSubreddit(id);
    }
}
