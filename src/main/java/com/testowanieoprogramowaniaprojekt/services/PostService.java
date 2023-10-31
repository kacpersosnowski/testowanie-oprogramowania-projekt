package com.testowanieoprogramowaniaprojekt.services;

import com.testowanieoprogramowaniaprojekt.entities.Post;
import com.testowanieoprogramowaniaprojekt.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Post save(Post post) {
        return postRepository.save(post);
    }

    public Post findById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found."));
    }

    public Post update(Long id, Post updatedPost) {
        return postRepository.findById(id)
                .map(currentPost -> {
                    currentPost.setTitle(updatedPost.getTitle());
                    currentPost.setDescription(updatedPost.getDescription());
                    return postRepository.save(currentPost);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found."));
    }

    public void deleteById(Long id) {
        postRepository.deleteById(id);
    }
}
