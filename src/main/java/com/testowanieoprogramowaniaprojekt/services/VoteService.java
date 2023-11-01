package com.testowanieoprogramowaniaprojekt.services;

import com.testowanieoprogramowaniaprojekt.entities.Vote;
import com.testowanieoprogramowaniaprojekt.repositories.CommentRepository;
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
    
    public List<Vote> findAll() {
        return voteRepository.findAll();
    }

    public Vote findById(Long id) {
        return voteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vote not found."));
    }

    public Vote save(Vote voteRequest) {
        Vote vote = commentRepository.findById(voteRequest.getComment().getId()).map(comment -> {
            voteRequest.setComment(comment);
            return voteRepository.save(voteRequest);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found"));;
        return vote;
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
}
