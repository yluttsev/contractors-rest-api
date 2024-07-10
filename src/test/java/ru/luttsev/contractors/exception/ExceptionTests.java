package ru.luttsev.contractors.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.luttsev.contractors.service.contractor.ContractorService;
import ru.luttsev.contractors.service.country.CountryService;
import ru.luttsev.contractors.service.industry.IndustryService;
import ru.luttsev.contractors.service.orgform.OrgFormService;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ExceptionTests {

    @MockBean
    private OrgFormService orgFormService;

    @MockBean
    private IndustryService industryService;

    @MockBean
    private CountryService countryService;

    @MockBean
    private ContractorService contractorService;

    @Test
    @DisplayName("Выбрасывание исключения OrgFormNotFoundException")
    public void testOrgFormNotFoundExceptionThrowing() {
        Integer invalidId = -1;
        when(orgFormService.getById(invalidId)).thenThrow(new OrgFormNotFoundException(invalidId));
        doThrow(new OrgFormNotFoundException(invalidId)).when(orgFormService).deleteById(invalidId);

        assertAll(
                () -> assertThrows(OrgFormNotFoundException.class, () -> orgFormService.getById(invalidId)),
                () -> assertThrows(OrgFormNotFoundException.class, () -> orgFormService.deleteById(invalidId))
        );
    }

    @Test
    @DisplayName("Выбрасывание исключения IndustryNotFoundException")
    public void testIndustryNotFoundExceptionThrowing() {
        Integer invalidId = -1;
        when(industryService.getById(invalidId)).thenThrow(new IndustryNotFoundException(invalidId));
        doThrow(new IndustryNotFoundException(invalidId)).when(industryService).deleteById(invalidId);

        assertAll(
                () -> assertThrows(IndustryNotFoundException.class, () -> industryService.getById(invalidId)),
                () -> assertThrows(IndustryNotFoundException.class, () -> industryService.deleteById(invalidId))
        );
    }

    @Test
    @DisplayName("Выбрасывание исключения CountryNotFoundException")
    public void testCountryNotFoundExceptionThrowing() {
        String invalidId = "invalidId";
        when(countryService.getById(invalidId)).thenThrow(new CountryNotFoundException(invalidId));
        doThrow(new CountryNotFoundException(invalidId)).when(countryService).deleteById(invalidId);

        assertAll(
                () -> assertThrows(CountryNotFoundException.class, () -> countryService.getById(invalidId)),
                () -> assertThrows(CountryNotFoundException.class, () -> countryService.deleteById(invalidId))
        );
    }

    @Test
    @DisplayName("Выбрасывание исключения ContractorNotFoundException")
    public void testContractorNotFoundExceptionThrowing() {
        String invalidId = "invalidId";
        when(contractorService.getById(invalidId)).thenThrow(new ContractorNotFoundException(invalidId));
        doThrow(new ContractorNotFoundException(invalidId)).when(contractorService).deleteById(invalidId);

        assertAll(
                () -> assertThrows(ContractorNotFoundException.class, () -> contractorService.getById(invalidId)),
                () -> assertThrows(ContractorNotFoundException.class, () -> contractorService.deleteById(invalidId))
        );
    }

}
