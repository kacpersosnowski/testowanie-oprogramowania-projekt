package com.testowanieoprogramowaniaprojekt.controllers;

import com.testowanieoprogramowaniaprojekt.entities.Post;
import com.testowanieoprogramowaniaprojekt.services.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    Collection<Post> findAll() {
        return postService.findAll();
    }

    @GetMapping("/{id}")
    Post findById(@PathVariable Long id) {
        return postService.findById(id);
    }

    @PostMapping
    Post create(@Valid @RequestBody Post post) {
        return postService.save(post);
    }

    @PutMapping("/{id}")
    Post update(@Valid @RequestBody Post post, @PathVariable Long id) {
        return postService.update(id, post);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id) {
        postService.deleteById(id);
    }
}
