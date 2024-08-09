package ru.luttsev.contractors.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
 *
 * @author Yuri Luttsev
 */
@Tag(name = "country", description = "API для работы со странами")
@RestController
@RequiredArgsConstructor
@RequestMapping("/country")
@PreAuthorize("!hasRole('ADMIN')")
public class CountryController {

    private final CountryService countryService;

    private final ModelMapper mapper;

    /**
     * Получение всех стран
     *
     * @return список {@link CountryResponsePayload DTO стран}
     */
    @Operation(summary = "Получение списка всех стран")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное получение списка всех стран",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = CountryResponsePayload.class)
                                    )
                            )
                    }
            )
    })
    @WebAuditLog(logLevel = LogLevel.INFO)
    @GetMapping("/all")
    @PreAuthorize("hasRole('USER')")
    public List<CountryResponsePayload> getAllCountries() {
        return countryService.getAll().stream()
                .map(country -> mapper.map(country, CountryResponsePayload.class))
                .collect(Collectors.toList());
    }

    /**
     * Получение страны по ID
     *
     * @param countryId ID страны
     * @return {@link CountryResponsePayload DTO} страны
     */
    @Operation(summary = "Получение страны по ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное получение страны по ID",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CountryResponsePayload.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Страна с указанным ID не найдена",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProblemDetail.class)
                            )
                    }
            )
    })
    @WebAuditLog(logLevel = LogLevel.INFO)
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public CountryResponsePayload getCountryById(@Parameter(description = "ID страны", required = true)
                                                 @PathVariable("id") String countryId) {
        Country country = countryService.getById(countryId);
        return mapper.map(country, CountryResponsePayload.class);
    }

    /**
     * Сохранение или обновление страны
     *
     * @param saveOrUpdateCountryPayload {@link SaveOrUpdateCountryPayload запрос} на сохранение или обновление страны
     * @return {@link CountryResponsePayload DTO} сохраненной или обновленной страны
     */
    @Operation(summary = "Сохранение страны", description = "Сохраняет страну с заранее указанным ID" +
            " или обновляет существующую страну")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное сохранение страны",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CountryResponsePayload.class)
                            )
                    }
            )
    })
    @WebAuditLog(logLevel = LogLevel.INFO)
    @PutMapping("/save")
    @PreAuthorize("hasAnyRole('CONTRACTOR_SUPERUSER', 'SUPERUSER')")
    public CountryResponsePayload saveOrUpdateCountry(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Страна для сохранения", required = true)
            @RequestBody SaveOrUpdateCountryPayload saveOrUpdateCountryPayload) {
        Country country = mapper.map(saveOrUpdateCountryPayload, Country.class);
        Country savedCountry = countryService.saveOrUpdate(country);
        return mapper.map(savedCountry, CountryResponsePayload.class);
    }

    /**
     * Удаление страны по ID
     *
     * @param countryId ID страны
     * @return ответ с кодом 200, если удаление прошло успешно
     */
    @Operation(summary = "Удаление страны по ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное удаление страны по ID"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Страна с указанным ID не найдена",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProblemDetail.class)
                            )
                    }
            )
    })
    @WebAuditLog(logLevel = LogLevel.INFO)
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('CONTRACTOR_SUPERUSER', 'SUPERUSER')")
    public ResponseEntity<?> deleteCountry(@Parameter(description = "ID страны")
                                           @PathVariable("id") String countryId) {
        countryService.deleteById(countryId);
        return ResponseEntity.ok().build();
    }

    /**
     * Обработчик 404 ошибки
     *
     * @param e {@link CountryNotFoundException класс исключения}
     * @return {@link ProblemDetail ответ} с деталями ошибки
     */
    @WebAuditLog(logLevel = LogLevel.INFO)
    @ExceptionHandler(value = CountryNotFoundException.class)
    public ProblemDetail countryNotFound(CountryNotFoundException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
    }

}
