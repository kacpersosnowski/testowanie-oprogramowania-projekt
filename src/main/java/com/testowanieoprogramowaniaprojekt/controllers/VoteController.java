package com.testowanieoprogramowaniaprojekt.controllers;

import com.testowanieoprogramowaniaprojekt.entities.Vote;
import com.testowanieoprogramowaniaprojekt.services.VoteService;
import lombok.RequiredArgsConstructor;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("votes")
@RequiredArgsConstructor
public class VoteController {
    private final VoteService voteService;

    @GetMapping
    List<Vote> findAll() {
        return voteService.findAll();
    }

    @GetMapping("/{id}")
    Vote findById(@PathVariable Long id) {
        return voteService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Vote create(@RequestBody Vote vote) {
        return voteService.save(vote);
    }
    
    @PutMapping("/{id}")
    Vote update(@PathVariable Long id, @RequestBody Vote vote) {
        return voteService.update(id, vote);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id) {
        voteService.deleteVote(id);
    }


}