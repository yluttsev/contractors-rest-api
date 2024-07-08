package ru.luttsev.contractors.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.luttsev.contractors.entity.Contractor;
import ru.luttsev.contractors.payload.contractor.ContractorFiltersPayload;
import ru.luttsev.contractors.repository.jdbc.ContractorJdbcRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Testcontainers
@Transactional
public class ContractorJdbcRepositoryTests {

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("contractors")
            .withUsername("postgres")
            .withPassword("postgres")
            .withInitScript("sql/init.sql");

    @Autowired
    private ContractorJdbcRepository contractorJdbcRepository;

    @DynamicPropertySource
    public static void setTestProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @Test
    @Sql("/sql/contractors_test.sql")
    @DisplayName("Поиск объекта Contractor по фильтрам")
    public void testFindContractorByFilters() {
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
