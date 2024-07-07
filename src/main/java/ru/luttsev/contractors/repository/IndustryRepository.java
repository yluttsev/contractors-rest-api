package ru.luttsev.contractors.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.luttsev.contractors.entity.Industry;

/**
 * Репозиторий для работы с объектами промышленности
 * @author Yuri Luttsev
 */
@Repository
public interface IndustryRepository extends JpaRepository<Industry, Integer> {
}
