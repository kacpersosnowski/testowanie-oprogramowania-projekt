package com.testowanieoprogramowaniaprojekt.repositories;

import com.testowanieoprogramowaniaprojekt.entities.Subreddit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubredditRepository extends JpaRepository<Subreddit, Long> {
}

