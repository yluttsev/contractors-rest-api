package ru.luttsev.contractors.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.luttsev.contractors.entity.Country;
import ru.luttsev.contractors.exception.CountryNotFoundException;
import ru.luttsev.contractors.payload.country.CountryResponsePayload;
import ru.luttsev.contractors.payload.country.SaveOrUpdateCountryPayload;
import ru.luttsev.contractors.service.country.CountryService;

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

@SpringBootTest
@AutoConfigureMockMvc
public class CountryControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CountryService countryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ModelMapper mapper;

    private Country createRussia() {
        return new Country("RUS", "Russia", true);
    }

    private Country createEngland() {
        return new Country("ENG", "England", true);
    }

    @Test
    @DisplayName(value = "Получение всех стран")
    public void testGetAllCountries() throws Exception {
        Country russia = createRussia();
        Country england = createEngland();

        when(countryService.getAll()).thenReturn(List.of(russia, england));

        mockMvc.perform(get("/country/all"))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.length()").value(2),
                        jsonPath("$[0].id").value(russia.getId()),
                        jsonPath("$[1].id").value(england.getId())
                );
    }

    @Test
    @DisplayName("Успешное получение страны по ID")
    public void testSuccessGetCountryById() throws Exception {
        Country expectedCountry = createRussia();
        when(countryService.getById(expectedCountry.getId())).thenReturn(expectedCountry);

        mockMvc.perform(get("/country/{id}", expectedCountry.getId()))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(expectedCountry.getId()),
                        jsonPath("$.name").value(expectedCountry.getName())
                );
    }

    @Test
    @DisplayName("Неуспешное получение страны по ID")
    public void testFailGetCountryById() throws Exception {
        String invalidId = "Invalid_ID";
        when(countryService.getById(invalidId)).thenThrow(new CountryNotFoundException(invalidId));

        mockMvc.perform(get("/country/{id}", invalidId))
                .andDo(print())
                .andExpectAll(
                        status().isNotFound(),
                        jsonPath("$.detail").value("Country with id: %s not found".formatted(invalidId))
                );
    }

    @Test
    @DisplayName("Создание новой страны")
    public void testCreateNewCountry() throws Exception {
        Country russia = createRussia();
        russia.setIsActive(null);
        SaveOrUpdateCountryPayload countryPayload = mapper.map(russia, SaveOrUpdateCountryPayload.class);
        when(countryService.saveOrUpdate(mapper.map(countryPayload, Country.class))).thenReturn(createRussia());

        mockMvc.perform(put("/country/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(countryPayload)))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().json(objectMapper.writeValueAsString(mapper.map(createRussia(), CountryResponsePayload.class)),
                                true)
                );
    }

    @Test
    @DisplayName("Удаление страны по ID")
    public void testSuccessDeleteCountryById() throws Exception {
        Country russia = createRussia();
        doNothing().when(countryService).deleteById(russia.getId());

        mockMvc.perform(delete("/country/delete/{id}", russia.getId()))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
