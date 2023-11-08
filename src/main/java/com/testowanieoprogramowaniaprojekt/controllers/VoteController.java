package com.testowanieoprogramowaniaprojekt.controllers;

import com.testowanieoprogramowaniaprojekt.entities.Vote;
import com.testowanieoprogramowaniaprojekt.services.VoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("votes")
@Tag(name = "Votes", description = "Votes management APIs")
@RequiredArgsConstructor
public class VoteController {
    private final VoteService voteService;

    @Operation(
            summary = "Find all Votes",
            description = "Get all Votes."
    )
    @GetMapping
    List<Vote> findAll() {
        return voteService.findAll();
    }

    @Operation(
            summary = "Find Vote by id",
            description = "Get a Vote by specifying their id."
    )
    @GetMapping("/{id}")
    Vote findById(
            @Parameter(description = "Vote id.", example = "1")
            @PathVariable Long id
    ) {
        return voteService.findById(id);
    }

    @Operation(
            summary = "Post a Vote",
            description = "Post a Vote to database."
    )

    @ApiResponse(
            responseCode = "200",
            content = {
                    @Content(schema = @Schema(implementation = Vote.class), mediaType = "application/json")})
    @ApiResponse(
            responseCode = "404",
            content = {@Content(schema = @Schema())})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Vote create(@RequestBody Vote vote) {
        return voteService.save(vote);
    }

    @Operation(
            summary = "Update Vote",
            description = "Put Vote by specifying their id and providing new Vote."
    )
    @PutMapping("/{id}")
    Vote update(
            @Parameter(description = "Vote id.", example = "1")
            @PathVariable Long id,
            @RequestBody Vote vote
    ) {
        return voteService.update(id, vote);
    }

    @Operation(
            summary = "Delete Vote by id",
            description = "Delete a Vote by specifying their id."
    )
    @DeleteMapping("/{id}")
    void delete(
            @Parameter(description = "Vote id.", example = "1")
            @PathVariable Long id
    ) {
        voteService.deleteVote(id);
    }
}
