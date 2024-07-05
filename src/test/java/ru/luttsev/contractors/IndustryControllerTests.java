package ru.luttsev.contractors;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.luttsev.contractors.controller.IndustryController;
import ru.luttsev.contractors.entity.Industry;
import ru.luttsev.contractors.exception.IndustryNotFoundException;
import ru.luttsev.contractors.payload.industry.IndustryResponsePayload;
import ru.luttsev.contractors.service.industry.IndustryService;

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

@WebMvcTest(controllers = IndustryController.class)
@AutoConfigureMockMvc
public class IndustryControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IndustryService industryService;

    @Autowired
    private ObjectMapper objectMapper;

    private Industry createItIndustry() {
        return new Industry(1, "Информационные технологии", true);
    }

    private Industry createFlyIndustry() {
        return new Industry(2, "Авиастроение", true);
    }

    @Test
    @DisplayName("Получение всех объектов промышленности")
    public void testGetAllIndustries() throws Exception {
        Industry it = createItIndustry();
        Industry fly = createFlyIndustry();

        when(industryService.getAll()).thenReturn(List.of(it, fly));

        mockMvc.perform(get("/industry/all"))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.length()").value(2),
                        jsonPath("$[0].id").value(it.getId()),
                        jsonPath("$[1].id").value(fly.getId()),
                        jsonPath("$[0].name").value(it.getName()),
                        jsonPath("$[1].name").value(fly.getName())
                );
    }

    @Test
    @DisplayName("Успешное получение объекта промышленности по ID")
    public void testSuccessGetIndustryById() throws Exception {
        Industry it = createItIndustry();

        when(industryService.getById(it.getId())).thenReturn(it);

        mockMvc.perform(get("/industry/{id}", it.getId()))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(it.getId()),
                        jsonPath("$.name").value(it.getName())
                );
    }

    @Test
    @DisplayName("Неуспешное получение объекта промышленности по ID")
    public void testFailGetIndustryById() throws Exception {
        Integer invalidId = -1;

        when(industryService.getById(invalidId)).thenThrow(new IndustryNotFoundException(invalidId));

        mockMvc.perform(get("/industry/{id}", invalidId))
                .andDo(print())
                .andExpectAll(
                        status().isNotFound(),
                        jsonPath("$.detail").value("Industry with id: %d not found".formatted(invalidId))
                );
    }

    @Test
    @DisplayName("Создание нового объекта промышленности")
    public void testCreateNewIndustry() throws Exception {
        Industry it = createItIndustry();

        when(industryService.saveOrUpdate(it)).thenReturn(it);

        mockMvc.perform(put("/industry/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new IndustryResponsePayload(it))))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().json(objectMapper.writeValueAsString(new IndustryResponsePayload(it)), true)
                );
    }

    @Test
    @DisplayName("Удаление объекта промышленности по ID")
    public void testDeleteIndustryById() throws Exception {
        Industry it = createItIndustry();

        doNothing().when(industryService).deleteById(it.getId());

        mockMvc.perform(delete("/industry/delete/{id}", it.getId()))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
