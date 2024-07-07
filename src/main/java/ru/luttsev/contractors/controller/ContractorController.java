package ru.luttsev.contractors.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
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
@RestController
@RequestMapping("/contractor")
@RequiredArgsConstructor
public class ContractorController {

    private final ContractorService contractorService;

    /**
     * Получение всех контрагентов
     * @return список {@link ContractorResponsePayload DTO контрагентов}
     */
    @WebAuditLog(logLevel = LogLevel.INFO)
    @GetMapping("/all")
    public List<ContractorResponsePayload> getAllContractors() {
        return contractorService.getAll().stream()
                .map(ContractorResponsePayload::new)
                .toList();
    }

    /**
     * Получение контрагента по ID
     * @param contractorId ID контрагента
     * @return {@link ContractorResponsePayload DTO} контрагента
     */
    @WebAuditLog(logLevel = LogLevel.INFO)
    @GetMapping("/{id}")
    public ContractorResponsePayload getContractorById(@PathVariable("id") String contractorId) {
        return new ContractorResponsePayload(contractorService.getById(contractorId));
    }

    /**
     * Сохранение или обновление контрагента
     * @param contractorPayload {@link SaveOrUpdateContractorPayload запрос} на сохранение или обновление контрагента
     * @return {@link ContractorResponsePayload DTO} сохраненного или обновленного контрагента
     */
    @WebAuditLog(logLevel = LogLevel.INFO)
    @PutMapping("/save")
    public ContractorResponsePayload saveOrUpdateContractor(@RequestBody SaveOrUpdateContractorPayload contractorPayload) {
        return new ContractorResponsePayload(contractorService.saveOrUpdate(contractorPayload.toEntity()));
    }

    /**
     * Удаление контрагента по ID
     * @param contractorId ID контрагента
     * @return ответ с кодом 200, если удаление прошло успешно
     */
    @WebAuditLog(logLevel = LogLevel.INFO)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteContractorById(@PathVariable("id") String contractorId) {
        contractorService.deleteById(contractorId);
        return ResponseEntity.ok().build();
    }

    /**
     * Поиск контрагента по фильтрам
     * @param contractorFilters {@link ContractorFiltersPayload фильтры} поиска
     * @param page номер страницы
     * @param contentSize количество элементов на странице
     * @return {@link ContractorsPagePayload страницу} с контрагентами
     */
    @WebAuditLog(logLevel = LogLevel.INFO)
    @PostMapping("/search")
    public ContractorsPagePayload searchContractors(
            @RequestBody ContractorFiltersPayload contractorFilters,
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int contentSize) {
        return contractorService.getByFilters(contractorFilters, page, contentSize);
    }

    /**
     * Поиск контрагента по фильтрам с помощью JDBC
     * @param contractorFilters {@link ContractorFiltersPayload фильтры} поиска
     * @param page номер страницы
     * @param contentSize количество элементов на странице
     * @return {@link ContractorsPagePayload страницу} с контрагентами
     */
    @WebAuditLog(logLevel = LogLevel.INFO)
    @PostMapping("/jdbc/search")
    public ContractorsPagePayload searchContractorsJdbc(@RequestBody ContractorFiltersPayload contractorFilters,
                                                        @RequestParam(defaultValue = "0", required = false) int page,
                                                        @RequestParam(defaultValue = "10", required = false) int contentSize) {
        return contractorService.getByFiltersJdbc(contractorFilters, page, contentSize);
    }

    /**
     * Обработчик 404 ошибки
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
