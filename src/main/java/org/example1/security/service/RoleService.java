package org.example1.security.service;

import org.example1.security.model.Role;
import org.example1.security.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class RoleService implements EntityService<Role> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleService.class);

    private final RoleRepository roleRepo;

    public RoleService(RoleRepository roleRepo) {
        this.roleRepo = roleRepo;
    }

    @Transactional
    @Override
    public void add(Role role) {
        LOGGER.info("Adding role: {}", role);
        roleRepo.save(role);
    }

    @Override
    public Optional<Role> findById(Long id) {
        return roleRepo.findById(id);
    }

    public Optional<Role> findByName(String name) {
        return roleRepo.findByName(name);
    }

    @Override
    public List<Role> findAll() {
        return roleRepo.findAll();
    }

    @Transactional
    @Override
    public void update(Role role) {
        LOGGER.info("Updating role: {}", role);
        roleRepo.save(role);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Role role = roleRepo.findById(id).orElse(null);
        if (role != null) {
            LOGGER.info("Deleting role with id {}: {}", id, role);
            roleRepo.delete(role);
        }
    }
}
