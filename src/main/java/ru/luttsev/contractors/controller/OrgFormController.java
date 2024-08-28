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
import ru.luttsev.contractors.entity.OrgForm;
import ru.luttsev.contractors.exception.OrgFormNotFoundException;
import ru.luttsev.contractors.payload.orgform.OrgFormResponsePayload;
import ru.luttsev.contractors.payload.orgform.SaveOrUpdateOrgFormPayload;
import ru.luttsev.contractors.service.orgform.OrgFormService;
import ru.luttsev.springbootstarterauditlib.LogLevel;
import ru.luttsev.springbootstarterauditlib.annotation.WebAuditLog;

import java.util.List;

/**
 * Контроллер для работы с объектами форм организаций
 *
 * @author Yuri Luttsev
 */
@Tag(name = "orgform", description = "API для работы с формами организации")
@RestController
@RequestMapping("/orgform")
@RequiredArgsConstructor
@PreAuthorize("!hasRole('ADMIN')")
public class OrgFormController {

    private final OrgFormService orgFormService;

    private final ModelMapper mapper;

    /**
     * Получение всех объектов форм организаций
     *
     * @return список {@link OrgFormResponsePayload DTO} объектов форм организаций
     */
    @Operation(summary = "Получение списка всех форм организации")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное получение списка всех форм организации",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = OrgFormResponsePayload.class)
                                    )
                            )
                    }
            )
    })
    @WebAuditLog(logLevel = LogLevel.INFO)
    @GetMapping("/all")
    @PreAuthorize("hasRole('USER')")
    public List<OrgFormResponsePayload> getAll() {
        return orgFormService.getAll().stream()
                .map(orgForm -> mapper.map(orgForm, OrgFormResponsePayload.class))
                .toList();
    }

    /**
     * Получение объекта формы организации по ID
     *
     * @param orgFormId ID объекта формы организации
     * @return {@link OrgFormResponsePayload DTO} объекта формы организации
     */
    @Operation(summary = "Получение формы организации по ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное получение формы организации по ID",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = OrgFormResponsePayload.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Форма организации с указанным ID не найдена",
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
    public OrgFormResponsePayload getById(@Parameter(description = "ID формы организации", required = true)
                                          @PathVariable("id") Integer orgFormId) {
        return mapper.map(orgFormService.getById(orgFormId), OrgFormResponsePayload.class);
    }

    /**
     * Сохранение или обновление объекта формы организации
     *
     * @param orgFormPayload {@link SaveOrUpdateOrgFormPayload запрос} на сохранение или обновление<br>
     *                       объекта формы организации
     * @return {@link OrgFormResponsePayload DTO} сохраненного или обновленного объекта формы организации
     */
    @Operation(summary = "Сохранение формы организации", description = "Сохранение формы организации с заранее указзаным ID" +
            " или обновление существующей формы организации")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное сохранение формы организации",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = OrgFormResponsePayload.class)
                            )
                    }
            )
    })
    @WebAuditLog(logLevel = LogLevel.INFO)
    @PutMapping("/save")
    @PreAuthorize("hasAnyRole('CONTRACTOR_SUPERUSER', 'SUPERUSER')")
    public OrgFormResponsePayload saveOrUpdate(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Форма организации для сохранения", required = true)
            @RequestBody SaveOrUpdateOrgFormPayload orgFormPayload) {
        OrgForm entity = mapper.map(orgFormPayload, OrgForm.class);
        return mapper.map(orgFormService.saveOrUpdate(entity), OrgFormResponsePayload.class);
    }

    /**
     * Удаление объекта формы организации по ID
     *
     * @param orgFormId ID объекта формы организации
     * @return ответ с кодом 200, если удаление прошло успешно
     */
    @Operation(summary = "Удаление формы организации по ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное удаление формы организации по ID"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Форма организации с указанным ID не найдена",
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
    public ResponseEntity<Void> deleteById(@Parameter(description = "ID формы организации", required = true)
                                           @PathVariable("id") Integer orgFormId) {
        orgFormService.deleteById(orgFormId);
        return ResponseEntity.ok().build();
    }

    /**
     * Обработчик 404 ошибки
     *
     * @param e {@link OrgFormNotFoundException класс исключения}
     * @return {@link ProblemDetail ответ} с деталями ошибки
     */
    @WebAuditLog(logLevel = LogLevel.INFO)
    @ExceptionHandler(OrgFormNotFoundException.class)
    public ProblemDetail orgFormNotFound(OrgFormNotFoundException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
    }

}
