package ru.luttsev.contractors.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.luttsev.contractors.service.contractor.ContractorService;
import ru.luttsev.contractors.service.country.CountryService;
import ru.luttsev.contractors.service.industry.IndustryService;
import ru.luttsev.contractors.service.orgform.OrgFormService;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExceptionTests {

    @Mock
    private OrgFormService orgFormService;

    @Mock
    private IndustryService industryService;

    @Mock
    private CountryService countryService;

    @Mock
    private ContractorService contractorService;

    @Test
    @DisplayName("Выбрасывание исключения OrgFormNotFoundException")
    void testOrgFormNotFoundExceptionThrowing() {
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
    void testIndustryNotFoundExceptionThrowing() {
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
    void testCountryNotFoundExceptionThrowing() {
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
    void testContractorNotFoundExceptionThrowing() {
        String invalidId = "invalidId";
        when(contractorService.getById(invalidId)).thenThrow(new ContractorNotFoundException(invalidId));
        doThrow(new ContractorNotFoundException(invalidId)).when(contractorService).deleteById(invalidId);

        assertAll(
                () -> assertThrows(ContractorNotFoundException.class, () -> contractorService.getById(invalidId)),
                () -> assertThrows(ContractorNotFoundException.class, () -> contractorService.deleteById(invalidId))
        );
    }

}
