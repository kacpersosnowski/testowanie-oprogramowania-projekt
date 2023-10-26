package com.testowanieoprogramowaniaprojekt.controllers;

import com.testowanieoprogramowaniaprojekt.entities.Subreddit;
import com.testowanieoprogramowaniaprojekt.services.SubredditService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/subreddits")
@RequiredArgsConstructor
public class SubredditController {
    private final SubredditService subredditService;

    @PostMapping
    public Subreddit createSubreddit(@RequestBody Subreddit subreddit) {
        return subredditService.createSubreddit(subreddit);
    }

    @GetMapping
    public List<Subreddit> getAllSubreddits() {
        return subredditService.getAllSubreddits();
    }

    @GetMapping("/{id}")
    public Subreddit getSubredditById(@PathVariable Long id) {
        return subredditService.findById(id);
    }

    @PutMapping("/{id}")
    public Subreddit updateSubreddit(@PathVariable Long id, @RequestBody Subreddit updatedSubreddit) {
        return subredditService.updateSubreddit(id, updatedSubreddit);
    }

    @DeleteMapping("/{id}")
    public void deleteSubreddit(@PathVariable Long id) {
        subredditService.deleteSubreddit(id);
    }
}
