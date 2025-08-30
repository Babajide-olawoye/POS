package com.AAA.demo.repos;

import com.AAA.demo.entities.User;
import org.springframework.data.repository.ListCrudRepository;

/**
 * Repository for {@link User} persistence operations.
 */
public interface UserRepository extends ListCrudRepository<User, Long> {
}
