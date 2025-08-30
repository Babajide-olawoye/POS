package com.AAA.demo.repos;

import com.AAA.demo.entities.Role;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link Role} entities.
 */
@Repository
public interface RoleRepository extends ListCrudRepository<Role, Long> {
}
