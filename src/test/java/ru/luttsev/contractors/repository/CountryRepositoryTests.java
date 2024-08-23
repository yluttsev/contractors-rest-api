package ru.luttsev.contractors.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.luttsev.contractors.PostgresContainer;
import ru.luttsev.contractors.entity.Country;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Import(PostgresContainer.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CountryRepositoryTests {

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    @DisplayName("Поиск объекта Country по ID")
    void testFindCountryById() {
        Country country = new Country("1", "Country 1", true);
        testEntityManager.merge(country);
        assertTrue(countryRepository.findById(country.getId()).isPresent());
    }

    @Test
    @DisplayName("Сохранение объекта Country")
    void testSaveCountry() {
        Country country = new Country("1", "Country 1", true);
        Country savedCountry = countryRepository.save(country);
        assertEquals(testEntityManager.find(Country.class, country.getId()), savedCountry);
    }

    @Test
    @DisplayName("Обновление объекта Country")
    void testUpdateCountry() {
        Country country = new Country("1", "Country 1", true);
        testEntityManager.merge(country);
        country.setName("Country 2");
        countryRepository.save(country);
        assertEquals(testEntityManager.find(Country.class, country.getId()).getName(), country.getName());
    }

    @Test
    @DisplayName("Удаление объекта Country по ID")
    void testDeleteCountryById() {
        Country country = new Country("1", "Country 1", true);
        testEntityManager.merge(country);
        countryRepository.deleteById(country.getId());
        assertNull(testEntityManager.find(Country.class, country.getId()));
    }

}
