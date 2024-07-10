package ru.luttsev.contractors.repository.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.luttsev.contractors.entity.Contractor;
import ru.luttsev.contractors.payload.contractor.ContractorFiltersPayload;

import java.util.List;

/**
 * Репозиторий для поиска контрагента по фильтрам с помощью JDBC
 * @author Yuri Luttsev
 */
@Repository
@RequiredArgsConstructor
public class ContractorJdbcRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final String BASE_QUERY = """
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

    /**
     * Получение контрагентов по фильтрам поиска
     * @param filters {@link ContractorFiltersPayload фильтры поиска}
     * @param page номер страницы
     * @param contentSize количество элементов на странице
     * @return {@link Page страница} с контрагентами
     */
    public Page<Contractor> getContractorsByFilters(ContractorFiltersPayload filters, int page, int contentSize) {
        StringBuilder query = new StringBuilder(BASE_QUERY);
        MapSqlParameterSource parameters = new MapSqlParameterSource();

        if (filters.getId() != null) {
            query.append(" and c.id = :c_id");
            parameters.addValue("c_id", filters.getId());
        }
        if (filters.getParentId() != null) {
            query.append(" and c.parent_id = :c_parent_id");
            parameters.addValue("c_parent_id", filters.getParentId());
        }
        if (filters.getName() != null) {
            query.append(" and c.name like :c_name");
            parameters.addValue("c_name", "%" + filters.getName() + "%");
        }
        if (filters.getFullName() != null) {
            query.append(" and c.name_full like :c_name_full");
            parameters.addValue("c_name_full", "%" + filters.getFullName() + "%");
        }
        if (filters.getInn() != null) {
            query.append(" and c.inn like :c_inn");
            parameters.addValue("c_inn", "%" + filters.getInn() + "%");
        }
        if (filters.getOgrn() != null) {
            query.append(" and c.ogrn like :c_ogrn");
            parameters.addValue("c_ogrn", "%" + filters.getOgrn() + "%");
        }
        if (filters.getCountryName() != null) {
            query.append(" and cn.name like :cn_name");
            parameters.addValue("cn_name", "%" + filters.getCountryName() + "%");
        }
        if (filters.getIndustry() != null) {
            query.append(" and i.id = :i_id").append(" and i.name = :i_name");
            parameters.addValue("i_id", filters.getIndustry().getId());
            parameters.addValue("i_name", filters.getIndustry().getName());
        }
        if (filters.getOrgFormName() != null) {
            query.append(" and of.name like :of_name");
            parameters.addValue("of_name", "%" + filters.getOrgFormName() + "%");
        }

        query.append(" limit :limit").append(" offset :offset");
        parameters.addValue("limit", contentSize);
        parameters.addValue("offset", page * contentSize);
        List<Contractor> contractors = jdbcTemplate.query(query.toString(), parameters, new ContractorRowMapper());

        return new PageImpl<>(contractors, Pageable.ofSize(contentSize), contractors.size());
    }

}
