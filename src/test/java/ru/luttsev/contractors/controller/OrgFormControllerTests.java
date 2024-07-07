package ru.luttsev.contractors.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.luttsev.contractors.entity.OrgForm;
import ru.luttsev.contractors.exception.OrgFormNotFoundException;
import ru.luttsev.contractors.payload.orgform.OrgFormResponsePayload;
import ru.luttsev.contractors.payload.orgform.SaveOrUpdateOrgFormPayload;
import ru.luttsev.contractors.service.orgform.OrgFormService;

import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = OrgFormController.class)
@AutoConfigureMockMvc
public class OrgFormControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrgFormService orgFormService;

    @Autowired
    private ObjectMapper objectMapper;

    private List<OrgForm> createOrgForms() {
        return List.of(
                new OrgForm(1, "OrgForm 1", true),
                new OrgForm(2, "OrgForm 2", true),
                new OrgForm(3, "OrgForm 3", true)
        );
    }

    @Test
    @DisplayName("Получение всех объектов OrgForm")
    public void testGetAllOrgForms() throws Exception {
        when(orgFormService.getAll()).thenReturn(createOrgForms());

        mockMvc.perform(get("/orgform/all"))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().json(
                                objectMapper.writeValueAsString(
                                        createOrgForms().stream().map(OrgFormResponsePayload::new).toList()
                                ),
                                true)
                );
    }

    @Test
    @DisplayName("Успешное получение объекта OrgForm по ID")
    public void testSuccessGetOrgFormById() throws Exception {
        List<OrgForm> orgForms = createOrgForms();

        when(orgFormService.getById(orgForms.get(0).getId())).thenReturn(orgForms.get(0));

        mockMvc.perform(get("/orgform/{id}", orgForms.get(0).getId()))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().json(
                                objectMapper.writeValueAsString(
                                        new OrgFormResponsePayload(orgForms.get(0))
                                ),
                                true)
                );
    }

    @Test
    @DisplayName("Неудачное получение объекта OrgForm по ID")
    public void testFailGetOrgFormById() throws Exception {
        Integer invalidId = -1;

        when(orgFormService.getById(invalidId)).thenThrow(new OrgFormNotFoundException(invalidId));

        mockMvc.perform(get("/orgform/{id}", invalidId))
                .andDo(print())
                .andExpectAll(
                        status().isNotFound(),
                        jsonPath("$.detail").value("Org form with id: %d not found".formatted(invalidId))
                );
    }

    @Test
    @DisplayName("Создание нового объекта OrgForm")
    public void testCreateNewOrgForm() throws Exception {
        OrgForm orgForm = createOrgForms().get(0);

        when(orgFormService.saveOrUpdate(orgForm)).thenReturn(orgForm);

        mockMvc.perform(put("/orgform/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new SaveOrUpdateOrgFormPayload(orgForm.getId(),
                                orgForm.getName()))))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().json(objectMapper.writeValueAsString(new OrgFormResponsePayload(orgForm)), true)
                );
    }

    @Test
    @DisplayName("Удаление объекта OrgForm по ID")
    public void testDeleteOrgFormById() throws Exception {
        OrgForm orgForm = createOrgForms().get(0);

        doNothing().when(orgFormService).deleteById(orgForm.getId());

        mockMvc.perform(delete("/orgform/delete/{id}", orgForm.getId()))
                .andDo(print())
                .andExpectAll(
                        status().isOk()
                );
    }

}
