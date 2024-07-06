package ru.luttsev.contractors.repository.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.luttsev.contractors.entity.Contractor;
import ru.luttsev.contractors.payload.contractor.ContractorFiltersPayload;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ContractorJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    private final String baseQuery = """
            select c.id as c_id, c.parent_id as c_parent_id, c.name as c_name, c.name_full as c_name_full, c.inn as c_inn,
            c.ogrn as c_ogrn, cn.id as cn_id, cn.name as cn_name, cn.is_active as cn_is_active,
            i.id as i_id, i.name as i_name, i.is_active as i_is_active,
            of.id as of_id, of.name as of_name, of.is_active as of_is_active, c.create_date as c_create_date,
            c.modify_date as c_modify_date, c.create_user_id as c_create_user_id, c.modify_user_id as c_modify_user_id,
            c.is_active as c_is_active
            from contractor c
            left join country cn on c.country = cn.id
            left join industry i on c.industry = i.id
            left join org_form of on c.org_form = of.id
            where c.is_active = true
            """;

    public Page<Contractor> getContractorsByFilters(ContractorFiltersPayload filters, int page, int contentSize) {
        StringBuilder query = new StringBuilder(baseQuery);

        if (filters.getId() != null) {
            query.append(" and c.id = '").append(filters.getId()).append("'");
        }
        if (filters.getParentId() != null) {
            query.append(" and c.parent_id = '").append(filters.getParentId()).append("'");
        }
        if (filters.getName() != null) {
            query.append(" and c.name like '%").append(filters.getName()).append("%'");
        }
        if (filters.getFullName() != null) {
            query.append(" and c.name_full like '%").append(filters.getFullName()).append("%'");
        }
        if (filters.getInn() != null) {
            query.append(" and c.inn like '%").append(filters.getInn()).append("%'");
        }
        if (filters.getOgrn() != null) {
            query.append(" and c.ogrn like '%").append(filters.getOgrn()).append("%'");
        }
        if (filters.getCountryName() != null) {
            query.append(" and cn.name like '%").append(filters.getCountryName()).append("%'");
        }
        if (filters.getIndustry() != null) {
            query.append(" and i.id = '").append(filters.getIndustry().getId()).append("'")
                    .append(" and i.name = '").append(filters.getIndustry().getName()).append("'");
        }
        if (filters.getOrgFormName() != null) {
            query.append(" and of.name like '%").append(filters.getOrgFormName()).append("%'");
        }

        query.append(" limit ").append(contentSize).append(" offset ").append(page * contentSize);
        List<Contractor> contractors = jdbcTemplate.query(query.toString(), new ContractorRowMapper());

        return new PageImpl<>(contractors, Pageable.ofSize(contentSize), contractors.size());
    }

}
