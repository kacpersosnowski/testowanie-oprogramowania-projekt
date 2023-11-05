package com.testowanieoprogramowaniaprojekt.services;

import com.testowanieoprogramowaniaprojekt.entities.Post;
import com.testowanieoprogramowaniaprojekt.entities.Subreddit;
import com.testowanieoprogramowaniaprojekt.entities.User;
import com.testowanieoprogramowaniaprojekt.exceptions.BadRequestException;
import com.testowanieoprogramowaniaprojekt.repositories.PostRepository;
import com.testowanieoprogramowaniaprojekt.repositories.SubredditRepository;
import com.testowanieoprogramowaniaprojekt.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final SubredditRepository subredditRepository;

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Post save(Post post) {
        validate(post);

        User author = userRepository.findById(post.getAuthor().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));
        Subreddit subreddit = subredditRepository.findById(post.getSubreddit().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Subreddit not found."));

        post.setAuthor(author);
        post.setSubreddit(subreddit);

        return postRepository.save(post);
    }

    public Post findById(Long id) {
        if(id < 0) {
            throw new BadRequestException("Post Id can't be less than 0.");
        }

        return postRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found."));
    }

    public Post update(Long id, Post updatedPost) {
        validate(updatedPost);

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

    private void validate(Post post) {
        if(post.getTitle() == null || post.getTitle().trim().isEmpty()) {
            throw new BadRequestException("Post title is mandatory.");
        } else if(post.getDescription() == null || post.getDescription().trim().isEmpty()) {
            throw new BadRequestException("Post description is mandatory.");
        } else if (post.getAuthor() == null) {
            throw new BadRequestException("Post author is mandatory.");
        } else if (post.getSubreddit() == null) {
            throw new BadRequestException("Post subreddit is mandatory.");
        }
    }

}
