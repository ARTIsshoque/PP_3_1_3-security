package org.example1.security.service;

import org.example1.security.model.User;
import org.example1.security.repository.RoleRepository;
import org.example1.security.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserService implements EntityService<User>, UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepo, RoleRepository roleRepo,
                       @Lazy PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public void add(User user) {
        LOGGER.info("Adding user: {}", user);
        String password = user.getPassword();
        if (password != null && !password.isEmpty()) {
            user.setPassword(passwordEncoder.encode(password));
        }
        roleRepo.saveAll(user.getRoles());
        userRepo.save(user);
    }

    @Override
    public User findById(Long id) {
        return userRepo.findById(id).orElse(null);
    }

    @Override
    public List<User> findAll() {
        return userRepo.findAll();
    }

    public List<User> findAllWithRoles() {
        return userRepo.findAllWithRoles();
    }

    @Transactional
    @Override
    public void update(User user) {
        LOGGER.info("Updating user: {}", user);
        String password = user.getPassword();
        if (password != null && !password.isEmpty()) {
            user.setPassword(passwordEncoder.encode(password));
        }
        userRepo.save(user);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        User user = userRepo.findById(id).orElse(null);
        if (user != null) {
            LOGGER.info("Deleting user with id {}: {}", id, user);
            userRepo.delete(user);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User " + username + " not found.");
        }
        return user;
    }

    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }
}
