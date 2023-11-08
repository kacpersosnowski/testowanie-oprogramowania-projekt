package com.testowanieoprogramowaniaprojekt.repositories;

import com.testowanieoprogramowaniaprojekt.entities.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long> {

}
