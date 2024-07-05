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

import java.util.List;

@RestController
@RequestMapping("/orgform")
@RequiredArgsConstructor
public class OrgFormController {

    private final OrgFormService orgFormService;

    @GetMapping("/all")
    public List<OrgFormResponsePayload> getAll() {
        return orgFormService.getAll().stream()
                .map(OrgFormResponsePayload::new)
                .toList();
    }

    @GetMapping("/{id}")
    public OrgFormResponsePayload getById(@PathVariable("id") Integer orgFormId) {
        return new OrgFormResponsePayload(orgFormService.getById(orgFormId));
    }

    @PutMapping("/save")
    public OrgFormResponsePayload saveOrUpdate(@RequestBody SaveOrUpdateOrgFormPayload orgFormPayload) {
        OrgForm entity = orgFormPayload.toEntity();
        return new OrgFormResponsePayload(orgFormService.saveOrUpdate(entity));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Integer orgFormId) {
        orgFormService.deleteById(orgFormId);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(OrgFormNotFoundException.class)
    public ProblemDetail orgFormNotFound(OrgFormNotFoundException e) {
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND, e.getMessage()
        );
    }

}
