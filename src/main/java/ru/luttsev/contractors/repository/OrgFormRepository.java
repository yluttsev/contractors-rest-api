package ru.luttsev.contractors.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.luttsev.contractors.entity.OrgForm;

@Repository
public interface OrgFormRepository extends JpaRepository<OrgForm, Integer> {
}
