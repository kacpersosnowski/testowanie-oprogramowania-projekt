package com.testowanieoprogramowaniaprojekt.repositories;

import com.testowanieoprogramowaniaprojekt.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
