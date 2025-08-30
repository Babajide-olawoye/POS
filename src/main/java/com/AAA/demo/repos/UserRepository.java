package com.AAA.demo.repos;

import com.AAA.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for {@link User} persistence operations.
 */
public interface UserRepository extends JpaRepository<User, Long> {
}
