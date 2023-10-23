package com.testowanieoprogramowaniaprojekt.repositories;

import com.testowanieoprogramowaniaprojekt.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
