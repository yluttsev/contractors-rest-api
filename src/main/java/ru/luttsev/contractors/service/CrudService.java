package ru.luttsev.contractors.service;

import java.util.List;

/**
 * Базовый интерфейс для CRUD сервисов
 * @param <T> сущность
 * @param <K> ID сущности
 */
public interface CrudService<T, K> {

    /**
     * Получение всех объектов
     * @return список объектов
     */
    List<T> getAll();

    /**
     * Получение объекта по ID
     * @param id ID объекта
     * @return объект
     */
    T getById(K id);

    /**
     * Сохранение или обновление объекта
     * @param entity сущность
     * @return сохарненная или обновленная сущность
     */
    T saveOrUpdate(T entity);

    /**
     * Удаление объекта по ID
     * @param id ID объекта
     */
    void deleteById(K id);

}
