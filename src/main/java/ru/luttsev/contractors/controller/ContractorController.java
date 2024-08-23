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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.luttsev.contractors.entity.Contractor;
import ru.luttsev.contractors.exception.ContractorNotFoundException;
import ru.luttsev.contractors.payload.contractor.ContractorFiltersPayload;
import ru.luttsev.contractors.payload.contractor.ContractorResponsePayload;
import ru.luttsev.contractors.payload.contractor.ContractorsPagePayload;
import ru.luttsev.contractors.payload.contractor.SaveOrUpdateContractorPayload;
import ru.luttsev.contractors.service.contractor.ContractorService;
import ru.luttsev.springbootstarterauditlib.LogLevel;
import ru.luttsev.springbootstarterauditlib.annotation.WebAuditLog;

import java.util.List;

/**
 * Контроллер для работы с контрагентами
 */
@Tag(name = "contractor", description = "API для работы с контрагентами")
@RestController
@RequestMapping("/contractor")
@RequiredArgsConstructor
public class ContractorController {

    private final ContractorService contractorService;

    private final ModelMapper mapper;

    /**
     * Получение всех контрагентов
     *
     * @return список {@link ContractorResponsePayload DTO контрагентов}
     */
    @Operation(summary = "Получение всех пользователей")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное получение списка всех контрагентов",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = ContractorResponsePayload.class)
                                    )
                            )
                    }
            )
    })
    @WebAuditLog(logLevel = LogLevel.INFO)
    @GetMapping("/all")
    public List<ContractorResponsePayload> getAllContractors() {
        return contractorService.getAll().stream()
                .map(contractor -> mapper.map(contractor, ContractorResponsePayload.class))
                .toList();
    }

    /**
     * Получение контрагента по ID
     *
     * @param contractorId ID контрагента
     * @return {@link ContractorResponsePayload DTO} контрагента
     */
    @Operation(summary = "Получение контрагента по ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное получение контрагента по ID",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ContractorResponsePayload.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Контрактор с указанным ID не найден",
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
    public ContractorResponsePayload getContractorById(@Parameter(description = "ID контрагента", required = true)
                                                       @PathVariable("id") String contractorId) {
        return mapper.map(contractorService.getById(contractorId), ContractorResponsePayload.class);
    }

    /**
     * Сохранение или обновление контрагента
     *
     * @param contractorPayload {@link SaveOrUpdateContractorPayload запрос} на сохранение или обновление контрагента
     * @return {@link ContractorResponsePayload DTO} сохраненного или обновленного контрагента
     */
    @Operation(summary = "Сохранение контрагента", description = "Сохраняет контрагента с заранее известным ID" +
            " или обновляет существующего контрагента")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Контрагент успешно сохранен или обновлен",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ContractorResponsePayload.class)
                            )
                    }
            )
    })
    @WebAuditLog(logLevel = LogLevel.INFO)
    @PutMapping("/save")
    public ContractorResponsePayload saveOrUpdateContractor(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Контрактор для сохранения", required = true)
            @RequestBody SaveOrUpdateContractorPayload contractorPayload) {
        Contractor savedContractor = contractorService.saveOrUpdate(mapper.map(contractorPayload, Contractor.class));
        return mapper.map(savedContractor, ContractorResponsePayload.class);
    }

    /**
     * Удаление контрагента по ID
     *
     * @param contractorId ID контрагента
     * @return ответ с кодом 200, если удаление прошло успешно
     */
    @Operation(summary = "Удаление контрагента по ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Контрагент успешно удален"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Контрагент с указанным ID не найден",
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
    public ResponseEntity<Void> deleteContractorById(@Parameter(description = "ID контрагента", required = true)
                                                     @PathVariable("id") String contractorId) {
        contractorService.deleteById(contractorId);
        return ResponseEntity.ok().build();
    }

    /**
     * Поиск контрагента по фильтрам
     *
     * @param contractorFilters {@link ContractorFiltersPayload фильтры} поиска
     * @param page              номер страницы
     * @param contentSize       количество элементов на странице
     * @return {@link ContractorsPagePayload страницу} с контрагентами
     */
    @Operation(summary = "Поиск котрагента по фильтрам", description = "Поиск контрагента по фильтрам с помощью Spring Specifications")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный поиск контрагента по указанным фильтрам",
                    content = {
                            @Content(schema = @Schema(implementation = ContractorsPagePayload.class))
                    }
            )
    })
    @WebAuditLog(logLevel = LogLevel.INFO)
    @PostMapping("/search")
    public ContractorsPagePayload searchContractors(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Фильтры поиска контрагентов", required = true)
            @RequestBody ContractorFiltersPayload contractorFilters,
            @Parameter(description = "Номер страницы", required = true) @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Количество элементов на странице", required = true) @RequestParam(defaultValue = "10") int contentSize,
            @AuthenticationPrincipal UserDetails userDetails) {
        return contractorService.getByFiltersWithCheckRole(contractorFilters, page, contentSize, userDetails);
    }

    /**
     * Поиск контрагента по фильтрам с помощью JDBC
     *
     * @param contractorFilters {@link ContractorFiltersPayload фильтры} поиска
     * @param page              номер страницы
     * @param contentSize       количество элементов на странице
     * @return {@link ContractorsPagePayload страницу} с контрагентами
     */
    @Operation(summary = "Поиск контрагента по фильтрам", description = "Поиск контрагента по фидьтрам с помощью JDBC")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный поиск контрагента по указанным фильтрам",
                    content = {
                            @Content(schema = @Schema(implementation = ContractorsPagePayload.class))
                    }
            )
    })
    @WebAuditLog(logLevel = LogLevel.INFO)
    @PostMapping("/jdbc/search")
    public ContractorsPagePayload searchContractorsJdbc(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Фильтры поиска контрагентов", required = true)
            @RequestBody ContractorFiltersPayload contractorFilters,
            @Parameter(description = "Номер страницы", required = true) @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Количество элементов на странице", required = true) @RequestParam(defaultValue = "10") int contentSize,
            @AuthenticationPrincipal UserDetails userDetails) {
        return contractorService.getByFiltersJdbcWithCheckRole(contractorFilters, page, contentSize, userDetails);
    }

    /**
     * Обработчик 404 ошибки
     *
     * @param e {@link ContractorNotFoundException класс исключения}
     * @return {@link ProblemDetail ответ} с деталями ошибки
     */
    @WebAuditLog(logLevel = LogLevel.INFO)
    @ExceptionHandler(ContractorNotFoundException.class)
    public ProblemDetail contractorNotFound(ContractorNotFoundException e) {
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND, e.getMessage()
        );
    }

}
