package com.testowanieoprogramowaniaprojekt.services;

import com.testowanieoprogramowaniaprojekt.entities.Subreddit;
import com.testowanieoprogramowaniaprojekt.entities.User;
import com.testowanieoprogramowaniaprojekt.exceptions.BadRequestException;
import com.testowanieoprogramowaniaprojekt.repositories.SubredditRepository;
import com.testowanieoprogramowaniaprojekt.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubredditService {
    private final SubredditRepository subredditRepository;
    private final UserRepository userRepository;

    public Subreddit createSubreddit(Subreddit subreddit) {
        validateSubreddit(subreddit);

        User user = userRepository.findById(subreddit.getUser().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));
        subreddit.setUser(user);
        return subredditRepository.save(subreddit);
    }

    public List<Subreddit> getAllSubreddits() {
        return subredditRepository.findAll();
    }

    public Subreddit findById(Long id) {
        return subredditRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Subreddit not found."));
    }

    public Subreddit updateSubreddit(Long id, Subreddit updatedSubreddit) {
        validateSubreddit(updatedSubreddit);

        return subredditRepository.findById(id)
                .map(currentSubreddit -> {
                    currentSubreddit.setName(updatedSubreddit.getName());
                    return subredditRepository.save(currentSubreddit);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Subreddit not found."));
    }

    public void deleteSubreddit(Long id) {
        subredditRepository.deleteById(id);
    }

    private void validateSubreddit(Subreddit subreddit) {
        if (subreddit.getName() == null || subreddit.getName().trim().isEmpty()) {
            throw new BadRequestException("Subreddit name is mandatory.");
        } else if (subreddit.getUser() == null) {
            throw new BadRequestException("Subreddit user is mandatory.");
        }
    }
}
