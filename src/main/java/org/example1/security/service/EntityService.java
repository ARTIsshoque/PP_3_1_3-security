package org.example1.security.service;

import java.util.List;
import java.util.Optional;

public interface EntityService <T> {

    void add(T entity);

    Optional<T> findById(Long id);

    List<T> findAll();

    void update(T entity);

    void delete(Long id);
}
