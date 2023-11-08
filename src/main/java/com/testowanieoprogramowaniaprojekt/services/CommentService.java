package com.testowanieoprogramowaniaprojekt.services;

import com.testowanieoprogramowaniaprojekt.entities.*;
import com.testowanieoprogramowaniaprojekt.exceptions.BadRequestException;
import com.testowanieoprogramowaniaprojekt.repositories.CommentRepository;
import com.testowanieoprogramowaniaprojekt.repositories.PostRepository;
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
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final VoteRepository voteRepository;

    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    public Comment findById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found."));
    }

    public Comment save(Comment commentRequest) {
        validateComment(commentRequest);

        User author = userRepository.findById(commentRequest.getAuthor().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        Post post = postRepository.findById(commentRequest.getPost().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));

        commentRequest.setAuthor(author);
        commentRequest.setPost(post);
        return commentRepository.save(commentRequest);
    }

    public Comment update(Long id, Comment comment) {
        validateComment(comment);

        return commentRepository.findById(id)
            .map(foundComment -> {
                foundComment.setContent(comment.getContent());
                return commentRepository.save(foundComment);
            })
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found."));
    }

    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }

    public int getVotes(Long id) {
        List<Vote> votes = voteRepository.findAll()
                .stream()
                .filter(vote -> vote.getComment() != null && Objects.equals(vote.getComment().getId(), id)).toList();
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

    private void validateComment(Comment comment) {
        if(comment.getContent() == null || comment.getContent().trim().isEmpty()) {
            throw new BadRequestException("Comment content is mandatory.");
        } else if(comment.getAuthor() == null) {
            throw new BadRequestException("Comment author is mandatory.");
        } else if(comment.getPost() == null) {
            throw new BadRequestException("Comment post is mandatory.");
        }
    }
}
