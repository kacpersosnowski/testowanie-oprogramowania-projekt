package com.testowanieoprogramowaniaprojekt.services;

import com.testowanieoprogramowaniaprojekt.entities.*;
import com.testowanieoprogramowaniaprojekt.exceptions.BadRequestException;
import com.testowanieoprogramowaniaprojekt.repositories.PostRepository;
import com.testowanieoprogramowaniaprojekt.repositories.SubredditRepository;
import com.testowanieoprogramowaniaprojekt.repositories.UserRepository;
import com.testowanieoprogramowaniaprojekt.repositories.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final SubredditRepository subredditRepository;
    private final VoteRepository voteRepository;

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

    public int getVotes(Long id) {
        List<Vote> votes = voteRepository.findAll()
                .stream()
                .filter(vote -> vote.getPost() != null && Objects.equals(vote.getPost().getId(), id)).toList();
        int result = 0;
        for (Vote vote: votes) {
            if (vote.getVoteType() == VoteType.POSITIVE) {
                result ++;
            } else {
                result--;
            }
        }
        return result;
    }

    private void validate(Post post) {
        if(post == null) {
            throw new BadRequestException("Post must not be null.");
        } else if(post.getTitle() == null || post.getTitle().trim().isEmpty()) {
            throw new BadRequestException("Post title is mandatory.");
        } else if(post.getDescription() == null || post.getDescription().trim().isEmpty()) {
            throw new BadRequestException("Post description is mandatory.");
        } else if(post.getAuthor() == null) {
            throw new BadRequestException("Post author is mandatory.");
        } else if(post.getSubreddit() == null) {
            throw new BadRequestException("Post subreddit is mandatory.");
        }
    }

}
