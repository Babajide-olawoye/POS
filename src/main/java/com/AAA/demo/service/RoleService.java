package com.AAA.demo.service;

import com.AAA.demo.entities.Role;
import com.AAA.demo.repos.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService  {
    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getAll() {
        return this.roleRepository.findAll();
    }

    public Optional<Role> getById(Long id) {
        return this.roleRepository.findById(id);
    }

    public Role create(Role role) {
        return (Role)this.roleRepository.save(role);
    }

    public void delete(Long id) {
        this.roleRepository.deleteById(id);
    }
}
