package ru.luttsev.contractors.service;

import java.util.List;

public interface EntityService<T, K> {

    List<T> getAll();

    T getById(K id);

    T saveOrUpdate(T entity);

    void deleteById(K id);

}
