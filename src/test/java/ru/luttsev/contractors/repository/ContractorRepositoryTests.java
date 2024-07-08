package ru.luttsev.contractors.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.luttsev.contractors.entity.Contractor;
import ru.luttsev.contractors.entity.Country;
import ru.luttsev.contractors.entity.Industry;
import ru.luttsev.contractors.entity.OrgForm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ContractorRepositoryTests {

    @Autowired
    private ContractorRepository contractorRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    private Contractor createContractor() {
        return Contractor.builder()
                .id("1")
                .name("Contractor 1")
                .country(new Country("1", "Country 1", true))
                .orgForm(new OrgForm(1, "OrgForm 1", true))
                .industry(new Industry(1, "Industry 1", true))
                .build();
    }

    @Test
    @DisplayName("Поиск объекта Contractor по ID")
    public void testFindContractorById() {
        Contractor contractor = createContractor();
        testEntityManager.merge(contractor);
        assertTrue(contractorRepository.findById(contractor.getId()).isPresent());
    }

    @Test
    @DisplayName("Сохранение объекта Contractor")
    public void testSaveIndustry() {
        Contractor contractor = createContractor();
        Contractor savedContractor = contractorRepository.save(contractor);
        assertEquals(testEntityManager.find(Contractor.class, contractor.getId()), savedContractor);
    }

    @Test
    @DisplayName("Обновление объекта Contractor")
    public void testUpdateContractor() {
        Contractor contractor = createContractor();
        testEntityManager.merge(contractor);
        contractor.setName("Contractor 2");
        contractorRepository.save(contractor);
        assertEquals(testEntityManager.find(Contractor.class, contractor.getId()).getName(), contractor.getName());
    }

    @Test
    @DisplayName("Удаление объекта Contractor по ID")
    public void testDeleteContractorById() {
        Contractor contractor = createContractor();
        testEntityManager.merge(contractor);
        contractorRepository.deleteById(contractor.getId());
        assertNull(testEntityManager.find(Contractor.class, contractor.getId()));
    }

}
