package com.testowanieoprogramowaniaprojekt.services;

import com.testowanieoprogramowaniaprojekt.entities.Comment;
import com.testowanieoprogramowaniaprojekt.repositories.CommentRepository;
import com.testowanieoprogramowaniaprojekt.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    public Comment findById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found."));
    }

    public Comment save(Comment commentRequest) {
        Comment comment = userRepository.findById(commentRequest.getAuthor().getId()).map(author -> {
            commentRequest.setAuthor(author);
            return commentRepository.save(commentRequest);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));;
        return comment;
    }

    public Comment update(Long id, Comment comment) {
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
}
