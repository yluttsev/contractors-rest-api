package ru.luttsev.contractors.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.luttsev.contractors.PostgresContainer;
import ru.luttsev.contractors.entity.Industry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Import(PostgresContainer.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class IndustryRepositoryTests {

    @Autowired
    private IndustryRepository industryRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    @DisplayName("Поиск объекта Industry по ID")
    void testFindIndustryById() {
        Industry industry = new Industry(1, "Industry 1", true);
        testEntityManager.merge(industry);
        assertTrue(industryRepository.findById(industry.getId()).isPresent());
    }

    @Test
    @DisplayName("Сохранение объекта Industry")
    void testSaveIndustry() {
        Industry industry = new Industry(1, "Industry 1", true);
        Industry savedIndustry = industryRepository.save(industry);
        assertEquals(testEntityManager.find(Industry.class, industry.getId()), savedIndustry);
    }

    @Test
    @DisplayName("Обновление объекта Industry")
    void testUpdateIndustry() {
        Industry industry = new Industry(1, "Industry 1", true);
        testEntityManager.merge(industry);
        industry.setName("Industry 2");
        industryRepository.save(industry);
        assertEquals(testEntityManager.find(Industry.class, industry.getId()).getName(), industry.getName());
    }

    @Test
    @DisplayName("Удаление объекта Industry по ID")
    void testDeleteIndustryById() {
        Industry industry = new Industry(1, "Industry 1", true);
        testEntityManager.merge(industry);
        industryRepository.deleteById(industry.getId());
        assertNull(testEntityManager.find(Industry.class, industry.getId()));
    }

}
