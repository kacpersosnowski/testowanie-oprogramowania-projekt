package com.testowanieoprogramowaniaprojekt.services;

import com.testowanieoprogramowaniaprojekt.entities.Comment;
import com.testowanieoprogramowaniaprojekt.entities.Post;
import com.testowanieoprogramowaniaprojekt.entities.User;
import com.testowanieoprogramowaniaprojekt.entities.Vote;
import com.testowanieoprogramowaniaprojekt.exceptions.BadRequestException;
import com.testowanieoprogramowaniaprojekt.repositories.CommentRepository;
import com.testowanieoprogramowaniaprojekt.repositories.PostRepository;
import com.testowanieoprogramowaniaprojekt.repositories.UserRepository;
import com.testowanieoprogramowaniaprojekt.repositories.VoteRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class VoteService {
    private final VoteRepository voteRepository;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    
    public List<Vote> findAll() {
        return voteRepository.findAll();
    }

    public Vote findById(Long id) {
        return voteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vote not found."));
    }

    public Vote save(Vote voteRequest) {
        int voteCategory = validateVote(voteRequest);

        
        User user = userRepository.findById(voteRequest.getUser().getId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        voteRequest.setUser(user);

        if (voteCategory == 0){
            Comment comment = commentRepository.findById(voteRequest.getComment().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found"));
            voteRequest.setComment(comment);
        } else if (voteCategory == 1){
            Post post = postRepository.findById(voteRequest.getPost().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));
            voteRequest.setPost(post);
        }
        
        return voteRepository.save(voteRequest);
    }

    public Vote update(Long id, Vote vote) {
        return voteRepository.findById(id)
            .map(foundVote -> {
                foundVote.setVoteType(vote.getVoteType());
                return voteRepository.save(foundVote);
            })
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vote not found."));
    }

    public void deleteVote(Long id) {
        voteRepository.deleteById(id);
    }

    private int validateVote(Vote vote) {
        if (vote.getVoteType() == null || vote.getUser() == null) {
            throw new BadRequestException("Vote type is mandatory.");
        }
        if (vote.getUser() == null) {
            throw new BadRequestException("User is mandatory.");
        }
        if(vote.getComment() == null && vote.getPost() == null) {
            throw new BadRequestException("Comment or post is mandatory.");
        } else if(vote.getComment() != null && vote.getPost() != null) {
            throw new BadRequestException("Only either post or comment is possible.");
        } else if(vote.getComment() != null && vote.getPost() == null) {
            return 0; //PostVote
        } else if(vote.getComment() == null && vote.getPost() != null) {
            return 1; //CommentVote
    }
    return -1;
}
}
