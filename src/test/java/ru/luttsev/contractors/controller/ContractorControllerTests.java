package ru.luttsev.contractors.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.test.web.servlet.MockMvc;
import ru.luttsev.contractors.entity.Contractor;
import ru.luttsev.contractors.entity.Country;
import ru.luttsev.contractors.entity.Industry;
import ru.luttsev.contractors.entity.OrgForm;
import ru.luttsev.contractors.exception.ContractorNotFoundException;
import ru.luttsev.contractors.payload.contractor.ContractorResponsePayload;
import ru.luttsev.contractors.payload.contractor.SaveOrUpdateContractorPayload;
import ru.luttsev.contractors.service.contractor.ContractorService;

import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ContractorControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContractorService contractorService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ModelMapper mapper;

    private Contractor createContractor(String id, String name) {
        return Contractor.builder()
                .id(id)
                .name(name)
                .fullName(name)
                .inn("123456")
                .ogrn("12345678")
                .orgForm(new OrgForm(1, "Test OrgForm", true))
                .country(new Country("RUS", "Russia", true))
                .industry(new Industry(1, "Test Industry", true))
                .isActive(true)
                .build();
    }

    @Test
    @DisplayName("Получение всех объектов Contractor")
    public void testGetAllContractors() throws Exception {
        List<Contractor> contractors = List.of(
                createContractor("1", "Contractor 1"),
                createContractor("2", "Contractor 2"),
                createContractor("3", "Contractor 3")
        );
        when(contractorService.getAll()).thenReturn(contractors);

        mockMvc.perform(get("/contractor/all"))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().json(
                                objectMapper.writeValueAsString(contractors.stream()
                                        .map(contractor -> mapper.map(contractor, ContractorResponsePayload.class))
                                        .toList()),
                                true
                        )
                );
    }

    @Test
    @DisplayName("Успешное получение объекта Contractor по ID")
    public void testSuccessGetContractorById() throws Exception {
        Contractor contractor = createContractor("1", "Contractor 1");
        when(contractorService.getById(contractor.getId())).thenReturn(contractor);

        mockMvc.perform(get("/contractor/{id}", contractor.getId()))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().json(
                                objectMapper.writeValueAsString(mapper.map(contractor, ContractorResponsePayload.class)),
                                true
                        )
                );
    }

    @Test
    @DisplayName("Неудачное получение объекта Contractor по ID")
    public void testFailGetContractorById() throws Exception {
        String invalidId = "invalidId";
        when(contractorService.getById(invalidId)).thenThrow(new ContractorNotFoundException(invalidId));

        mockMvc.perform(get("/contractor/{id}", invalidId))
                .andDo(print())
                .andExpectAll(
                        status().isNotFound(),
                        content().json(
                                objectMapper.writeValueAsString(ProblemDetail.forStatusAndDetail(
                                        HttpStatus.NOT_FOUND, "Contractor with id: %s not found".formatted(invalidId)
                                ))
                        )
                );
    }

    @Test
    @DisplayName("Создание нового объекта Contractor")
    public void testCreateNewContractor() throws Exception {
        Contractor contractor = createContractor("1", "Contractor 1");
        SaveOrUpdateContractorPayload contractorPayload = mapper.map(contractor,
                SaveOrUpdateContractorPayload.class);
        when(contractorService.saveOrUpdate(mapper.map(contractorPayload, Contractor.class)))
                .thenReturn(createContractor("1", "Contractor 1"));

        mockMvc.perform(put("/contractor/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contractorPayload)))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().json(
                                objectMapper.writeValueAsString(mapper.map(contractor, ContractorResponsePayload.class)),
                                true
                        )
                );
    }

    @Test
    @DisplayName("Удаление объекта Contractor по ID")
    public void testDeleteContractorById() throws Exception {
        Contractor contractor = createContractor("1", "Contractor 1");
        doNothing().when(contractorService).deleteById(contractor.getId());

        mockMvc.perform(delete("/contractor/delete/{id}", contractor.getId()))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
