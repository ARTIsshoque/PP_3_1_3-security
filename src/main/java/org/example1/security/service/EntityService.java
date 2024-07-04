package org.example1.security.service;

import java.util.List;

public interface EntityService <T> {

    void add(T entity);

    T findById(Long id);

    List<T> findAll();

    void update(T entity);

    void delete(Long id);
}
