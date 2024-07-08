package ru.luttsev.contractors.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.luttsev.contractors.entity.Country;
import ru.luttsev.contractors.exception.CountryNotFoundException;
import ru.luttsev.contractors.payload.country.CountryResponsePayload;
import ru.luttsev.contractors.payload.country.SaveOrUpdateCountryPayload;
import ru.luttsev.contractors.service.country.CountryService;
import ru.luttsev.springbootstarterauditlib.LogLevel;
import ru.luttsev.springbootstarterauditlib.annotation.WebAuditLog;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Контроллер для работы со странами
 * @author Yuri Luttsev
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/country")
public class CountryController {

    private final CountryService countryService;

    private final ModelMapper mapper;

    /**
     * Получение всех стран
     * @return список {@link CountryResponsePayload DTO стран}
     */
    @WebAuditLog(logLevel = LogLevel.INFO)
    @GetMapping("/all")
    public List<CountryResponsePayload> getAllCountries() {
        return countryService.getAll().stream()
                .map(country -> mapper.map(country, CountryResponsePayload.class))
                .collect(Collectors.toList());
    }

    /**
     * Получение страны по ID
     * @param countryId ID страны
     * @return {@link CountryResponsePayload DTO} страны
     */
    @WebAuditLog(logLevel = LogLevel.INFO)
    @GetMapping("/{id}")
    public CountryResponsePayload getCountryById(@PathVariable("id") String countryId) {
        Country country = countryService.getById(countryId);
        return mapper.map(country, CountryResponsePayload.class);
    }

    /**
     * Сохранение или обновление страны
     * @param saveOrUpdateCountryPayload {@link SaveOrUpdateCountryPayload запрос} на сохранение или обновление страны
     * @return {@link CountryResponsePayload DTO} сохраненной или обновленной страны
     */
    @WebAuditLog(logLevel = LogLevel.INFO)
    @PutMapping("/save")
    public CountryResponsePayload saveOrUpdateCountry(@RequestBody SaveOrUpdateCountryPayload saveOrUpdateCountryPayload) {
        Country country = mapper.map(saveOrUpdateCountryPayload, Country.class);
        Country savedCountry = countryService.saveOrUpdate(country);
        return mapper.map(savedCountry, CountryResponsePayload.class);
    }

    /**
     * Удаление страны по ID
     * @param countryId ID страны
     * @return ответ с кодом 200, если удаление прошло успешно
     */
    @WebAuditLog(logLevel = LogLevel.INFO)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCountry(@PathVariable("id") String countryId) {
        countryService.deleteById(countryId);
        return ResponseEntity.ok().build();
    }

    /**
     * Обработчик 404 ошибки
     * @param e {@link CountryNotFoundException класс исключения}
     * @return {@link ProblemDetail ответ} с деталями ошибки
     */
    @WebAuditLog(logLevel = LogLevel.INFO)
    @ExceptionHandler(value = CountryNotFoundException.class)
    public ProblemDetail countryNotFound(CountryNotFoundException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
    }

}
