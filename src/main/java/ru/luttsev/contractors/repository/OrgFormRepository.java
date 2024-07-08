package ru.luttsev.contractors.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.luttsev.contractors.entity.OrgForm;

/**
 * Репозиторий для работы с объектами форм организаций
 * @author Yuri Luttsev
 */
@Repository
public interface OrgFormRepository extends JpaRepository<OrgForm, Integer> {
}
