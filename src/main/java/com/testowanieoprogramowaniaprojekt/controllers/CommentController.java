package com.testowanieoprogramowaniaprojekt.controllers;

import com.testowanieoprogramowaniaprojekt.entities.Comment;
import com.testowanieoprogramowaniaprojekt.exceptions.BadRequestException;
import com.testowanieoprogramowaniaprojekt.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handleException(BadRequestException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity<List<Comment>> getAllComments() {
        return new ResponseEntity<>(commentService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable("id") long id) {
        return new ResponseEntity<>(commentService.findById(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment) {
        if(comment.getContent() == null || comment.getContent().trim().isEmpty()) {
            throw new BadRequestException("Comment content is mandatory.");
        } else if(comment.getAuthor() == null) {
            throw new BadRequestException("Comment author is mandatory.");
//        }
        } else if(comment.getPost() == null) {
            throw new BadRequestException("Comment post is mandatory.");
        }
        return new ResponseEntity<>(commentService.save(comment), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable("id") long id, @RequestBody Comment comment) {
        if(comment.getContent() == null || comment.getContent().trim().isEmpty()) {
            throw new BadRequestException("Comment content is mandatory.");
        } else if(comment.getAuthor() == null) {
            throw new BadRequestException("Comment author is mandatory.");
        } else if(comment.getPost() == null) {
            throw new BadRequestException("Comment post is mandatory.");
        }
        return new ResponseEntity<>(commentService.update(id, comment), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable Long id) {
        commentService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
