package ru.luttsev.contractors.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.luttsev.contractors.entity.Country;

/**
 * Репозиторий для работы со странами
 * @author Yuri Luttsev
 */
@Repository
public interface CountryRepository extends JpaRepository<Country, String> {
}
