package ru.luttsev.contractors.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.luttsev.contractors.entity.OrgForm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OrgFormRepositoryTests {

    @Autowired
    private OrgFormRepository orgFormRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    @DisplayName("Поиск объекта OrgForm по ID")
    public void testFindOrgFormById() {
        OrgForm orgForm = new OrgForm(1, "OrgForm 1", true);
        testEntityManager.merge(orgForm);
        assertTrue(orgFormRepository.findById(orgForm.getId()).isPresent());
    }

    @Test
    @DisplayName("Сохранение объекта OrgForm")
    public void testSaveOrgForm() {
        OrgForm orgForm = new OrgForm(1, "OrgForm 1", true);
        OrgForm savedOrgForm = orgFormRepository.save(orgForm);
        assertEquals(testEntityManager.find(OrgForm.class, orgForm.getId()), savedOrgForm);
    }

    @Test
    @DisplayName("Обновление объекта OrgForm")
    public void testUpdateOrgForm() {
        OrgForm orgForm = new OrgForm(1, "OrgForm 1", true);
        testEntityManager.merge(orgForm);
        orgForm.setName("OrgForm 2");
        orgFormRepository.save(orgForm);
        assertEquals(testEntityManager.find(OrgForm.class, orgForm.getId()).getName(), orgForm.getName());
    }

    @Test
    @DisplayName("Удаление объекта OrgForm по ID")
    public void testDeleteOrgFormById() {
        OrgForm orgForm = new OrgForm(1, "OrgForm 1", true);
        testEntityManager.merge(orgForm);
        orgFormRepository.deleteById(orgForm.getId());
        assertNull(testEntityManager.find(OrgForm.class, orgForm.getId()));
    }

}
