package com.AAA.demo.service;

import com.AAA.demo.entities.Role;
import com.AAA.demo.repos.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for {@link Role} entities.
 */
@Service
public class RoleService {

    private final RoleRepository roleRepository;

    /**
     * Creates a new instance using constructor injection.
     *
     * @param roleRepository repository for role persistence
     */
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * Retrieves all roles.
     */
    public List<Role> getAll() {
        return this.roleRepository.findAll();
    }

    /**
     * Looks up a role by id.
     */
    public Optional<Role> getById(Long id) {
        return this.roleRepository.findById(id);
    }

    /**
     * Creates a new role entity.
     */
    public Role create(Role role) {
        return this.roleRepository.save(role);
    }

    /**
     * Deletes a role by id.
     */
    public void delete(Long id) {
        this.roleRepository.deleteById(id);
    }
}
