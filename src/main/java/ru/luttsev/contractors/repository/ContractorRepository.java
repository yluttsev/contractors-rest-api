package ru.luttsev.contractors.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.luttsev.contractors.entity.Contractor;

@Repository
public interface ContractorRepository extends JpaRepository<Contractor, String>, JpaSpecificationExecutor<Contractor> {
}
