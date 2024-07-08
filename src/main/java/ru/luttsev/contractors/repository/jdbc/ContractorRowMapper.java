package ru.luttsev.contractors.repository.jdbc;

import org.springframework.jdbc.core.RowMapper;
import ru.luttsev.contractors.entity.Contractor;
import ru.luttsev.contractors.entity.Country;
import ru.luttsev.contractors.entity.Industry;
import ru.luttsev.contractors.entity.OrgForm;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * RowMapper для преобразования ResultSet в объект контрагента
 * @author Yuri Luttsev
 */
public class ContractorRowMapper implements RowMapper<Contractor> {

    @Override
    public Contractor mapRow(ResultSet rs, int rowNum) throws SQLException {
        Industry industry = Industry.builder()
                .id(rs.getInt("i_id"))
                .name(rs.getString("i_name"))
                .isActive(rs.getBoolean("i_is_active"))
                .build();

        Country country = Country.builder()
                .id(rs.getString("cn_id"))
                .name(rs.getString("cn_name"))
                .isActive(rs.getBoolean("cn_is_active"))
                .build();

        OrgForm orgForm = OrgForm.builder()
                .id(rs.getInt("of_id"))
                .name(rs.getString("of_name"))
                .isActive(rs.getBoolean("of_is_active"))
                .build();

        return Contractor.builder()
                .id(rs.getString("c_id"))
                .parentId(rs.getString("c_parent_id"))
                .name(rs.getString("c_name"))
                .fullName(rs.getString("c_name_full"))
                .inn(rs.getString("c_inn"))
                .ogrn(rs.getString("c_ogrn"))
                .country(country)
                .industry(industry)
                .orgForm(orgForm)
                .createDate(rs.getTimestamp("c_create_date") != null ? rs.getTimestamp("c_create_date").toLocalDateTime() : null)
                .modifyDate(rs.getTimestamp("c_modify_date") != null ? rs.getTimestamp("c_modify_date").toLocalDateTime() : null)
                .createUserId(rs.getString("c_create_user_id"))
                .modifyUserId(rs.getString("c_modify_user_id"))
                .isActive(rs.getBoolean("c_is_active"))
                .build();
    }

}
