package ru.luttsev.contractors.controller;

import lombok.RequiredArgsConstructor;
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
 * @author Yuri Luttsev
 */
@RestController
@RequestMapping("/orgform")
@RequiredArgsConstructor
public class OrgFormController {

    private final OrgFormService orgFormService;

    /**
     * Получение всех объектов форм организаций
     * @return список {@link OrgFormResponsePayload DTO} объектов форм организаций
     */
    @WebAuditLog(logLevel = LogLevel.INFO)
    @GetMapping("/all")
    public List<OrgFormResponsePayload> getAll() {
        return orgFormService.getAll().stream()
                .map(OrgFormResponsePayload::new)
                .toList();
    }

    /**
     * Получение объекта формы организации по ID
     * @param orgFormId ID объекта формы организации
     * @return {@link OrgFormResponsePayload DTO} объекта формы организации
     */
    @WebAuditLog(logLevel = LogLevel.INFO)
    @GetMapping("/{id}")
    public OrgFormResponsePayload getById(@PathVariable("id") Integer orgFormId) {
        return new OrgFormResponsePayload(orgFormService.getById(orgFormId));
    }

    /**
     * Сохранение или обновление объекта формы организации
     * @param orgFormPayload {@link SaveOrUpdateOrgFormPayload запрос} на сохранение или обновление<br>
     *                                                                объекта формы организации
     * @return {@link OrgFormResponsePayload DTO} сохраненного или обновленного объекта формы организации
     */
    @WebAuditLog(logLevel = LogLevel.INFO)
    @PutMapping("/save")
    public OrgFormResponsePayload saveOrUpdate(@RequestBody SaveOrUpdateOrgFormPayload orgFormPayload) {
        OrgForm entity = orgFormPayload.toEntity();
        return new OrgFormResponsePayload(orgFormService.saveOrUpdate(entity));
    }

    /**
     * Удаление объекта формы организации по ID
     * @param orgFormId ID объекта формы организации
     * @return ответ с кодом 200, если удаление прошло успешно
     */
    @WebAuditLog(logLevel = LogLevel.INFO)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Integer orgFormId) {
        orgFormService.deleteById(orgFormId);
        return ResponseEntity.ok().build();
    }

    /**
     * Обработчик 404 ошибки
     * @param e {@link OrgFormNotFoundException класс исключения}
     * @return {@link ProblemDetail ответ} с деталями ошибки
     */
    @WebAuditLog(logLevel = LogLevel.INFO)
    @ExceptionHandler(OrgFormNotFoundException.class)
    public ProblemDetail orgFormNotFound(OrgFormNotFoundException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
    }

}
