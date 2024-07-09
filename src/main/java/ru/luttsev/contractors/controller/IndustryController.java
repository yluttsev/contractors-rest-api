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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.luttsev.contractors.entity.Industry;
import ru.luttsev.contractors.exception.IndustryNotFoundException;
import ru.luttsev.contractors.payload.industry.IndustryResponsePayload;
import ru.luttsev.contractors.payload.industry.SaveOrUpdateIndustryPayload;
import ru.luttsev.contractors.service.industry.IndustryService;
import ru.luttsev.springbootstarterauditlib.LogLevel;
import ru.luttsev.springbootstarterauditlib.annotation.WebAuditLog;

import java.util.List;

/**
 * Контроллер для рботы с объектами промышленности
 *
 * @author Yuri Luttsev
 */
@Tag(name = "industry", description = "API для работы с промышленностями")
@RestController
@RequiredArgsConstructor
@RequestMapping("/industry")
public class IndustryController {

    private final IndustryService industryService;

    private final ModelMapper mapper;

    /**
     * Получение всех объектов промышленности
     *
     * @return список {@link IndustryResponsePayload DTO} промышленностей
     */
    @Operation(summary = "Получение списка всех промышленностей")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное получение списка всех промышленностей",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = IndustryResponsePayload.class)
                                    )
                            )
                    }
            )
    })
    @WebAuditLog(logLevel = LogLevel.INFO)
    @GetMapping("/all")
    public List<IndustryResponsePayload> getAllIndustries() {
        return industryService.getAll().stream()
                .map(industry -> mapper.map(industry, IndustryResponsePayload.class))
                .toList();
    }

    /**
     * Получение объекта промышленности по ID
     *
     * @param id ID объекта промышленности
     * @return {@link IndustryResponsePayload DTO} объекта промышленности
     */
    @Operation(summary = "Получение промышленности по ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное получение промышленности по ID",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = IndustryResponsePayload.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Промышленность с указанным ID не найдена",
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
    public IndustryResponsePayload getIndustryById(@Parameter(description = "ID промышленности", required = true)
                                                   @PathVariable("id") Integer id) {
        Industry industry = industryService.getById(id);
        return mapper.map(industry, IndustryResponsePayload.class);
    }

    /**
     * Сохранение или обновление объекта промышленности
     *
     * @param saveOrUpdateIndustryPayload {@link SaveOrUpdateIndustryPayload запрос} на сохранение или обновление<br>
     *                                    объекта промышленности
     * @return {@link IndustryResponsePayload DTO} сохраненного или обновленного объекта промышленности
     */
    @Operation(summary = "Сохранение промышленности", description = "Сохранение промышленности с заранее известным ID" +
            " или обновление существующей промышленности")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное сохранение промышленности",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = IndustryResponsePayload.class)
                            )
                    }
            )
    })
    @WebAuditLog(logLevel = LogLevel.INFO)
    @PutMapping("/save")
    public IndustryResponsePayload saveOrUpdateIndustry(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Промышленность для сохранения", required = true)
            @RequestBody SaveOrUpdateIndustryPayload saveOrUpdateIndustryPayload) {
        Industry industry = mapper.map(saveOrUpdateIndustryPayload, Industry.class);
        Industry savedIndustry = industryService.saveOrUpdate(industry);
        return mapper.map(savedIndustry, IndustryResponsePayload.class);
    }

    /**
     * Удаление объекта промышленности по ID
     *
     * @param industryId ID объекта промышленности
     * @return ответ с кодом 200, если удаление прошло успешно
     */
    @Operation(summary = "Удаление промышленности по ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное удаление промышленности по ID"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Промышленность с указанным ID не найдена",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = IndustryResponsePayload.class)
                            )
                    }
            )
    })
    @WebAuditLog(logLevel = LogLevel.INFO)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCountry(@Parameter(description = "ID промышленности", required = true)
                                           @PathVariable("id") Integer industryId) {
        industryService.deleteById(industryId);
        return ResponseEntity.ok().build();
    }

    /**
     * Обработчик 404 ошибки
     *
     * @param e {@link IndustryNotFoundException класс исключения}
     * @return {@link ProblemDetail ответ} с деталями ошибки
     */
    @WebAuditLog(logLevel = LogLevel.INFO)
    @ExceptionHandler(IndustryNotFoundException.class)
    public ProblemDetail industryNotFound(IndustryNotFoundException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
    }

}
