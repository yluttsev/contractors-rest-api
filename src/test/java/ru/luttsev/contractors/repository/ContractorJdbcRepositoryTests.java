package ru.luttsev.contractors.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.test.context.jdbc.Sql;
import ru.luttsev.contractors.PostgresContainer;
import ru.luttsev.contractors.entity.Contractor;
import ru.luttsev.contractors.payload.contractor.ContractorFiltersPayload;
import ru.luttsev.contractors.repository.jdbc.ContractorJdbcRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Import(PostgresContainer.class)
@Transactional
class ContractorJdbcRepositoryTests {

    @Autowired
    private ContractorJdbcRepository contractorJdbcRepository;

    @Test
    @Sql("/sql/contractors_test.sql")
    @DisplayName("Поиск объекта Contractor по фильтрам")
    void testFindContractorByFilters() {
        ContractorFiltersPayload filters = ContractorFiltersPayload.builder()
                .name("Contractor 1")
                .countryName("Российская Федерация")
                .build();

        Page<Contractor> contractorPage = contractorJdbcRepository.getContractorsByFilters(filters, 0, 10);
        List<Contractor> contractors = contractorPage.getContent();

        assertEquals(contractors.get(0).getName(), filters.getName());
        assertEquals(contractors.get(0).getCountry().getName(), filters.getCountryName());
    }

}
