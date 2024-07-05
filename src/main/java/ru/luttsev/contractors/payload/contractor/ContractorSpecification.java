package ru.luttsev.contractors.payload.contractor;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import ru.luttsev.contractors.entity.Contractor;
import ru.luttsev.contractors.entity.Country;
import ru.luttsev.contractors.entity.Industry;
import ru.luttsev.contractors.entity.OrgForm;

import java.util.ArrayList;
import java.util.List;

public final class ContractorSpecification {

    private ContractorSpecification() {
    }

    public static Specification<Contractor> getContractorByFilters(ContractorFiltersPayload contractorFilters) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(criteriaBuilder.isTrue(root.get("isActive")));

            if (contractorFilters.getId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("id"), contractorFilters.getId()));
            }
            if (contractorFilters.getParentId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("parentId"), contractorFilters.getId()));
            }
            if (contractorFilters.getName() != null) {
                predicates.add(criteriaBuilder.like(root.get("name"), contractorFilters.getName()));
            }
            if (contractorFilters.getFullName() != null) {
                predicates.add(criteriaBuilder.like(root.get("fullName"), contractorFilters.getFullName()));
            }
            if (contractorFilters.getInn() != null) {
                predicates.add(criteriaBuilder.like(root.get("inn"), contractorFilters.getInn()));
            }
            if (contractorFilters.getOgrn() != null) {
                predicates.add(criteriaBuilder.like(root.get("ogrn"), contractorFilters.getOgrn()));
            }
            if (contractorFilters.getCountryName() != null) {
                Join<Contractor, Country> contractorCountries = root.join("country");
                predicates.add(criteriaBuilder.like(contractorCountries.get("name"), contractorFilters.getCountryName()));
            }
            if (contractorFilters.getIndustry() != null) {
                Join<Contractor, Industry> contractorIndustries = root.join("industry");
                predicates.addAll(List.of(
                        criteriaBuilder.equal(contractorIndustries.get("id"),
                                contractorFilters.getIndustry().getId()),
                        criteriaBuilder.equal(contractorIndustries.get("name"),
                                contractorFilters.getIndustry().getName()))
                );
            }
            if (contractorFilters.getOrgFormName() != null) {
                Join<Contractor, OrgForm> contractorOrgForms = root.join("orgForm");
                predicates.add(criteriaBuilder.like(contractorOrgForms.get("name"), contractorFilters.getOrgFormName()));
            }

            return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
        };
    }

}
