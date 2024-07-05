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
import ru.luttsev.contractors.entity.Industry;
import ru.luttsev.contractors.exception.IndustryNotFoundException;
import ru.luttsev.contractors.payload.industry.IndustryResponsePayload;
import ru.luttsev.contractors.payload.industry.SaveOrUpdateIndustryPayload;
import ru.luttsev.contractors.service.industry.IndustryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/industry")
public class IndustryController {

    private final IndustryService industryService;

    @GetMapping("/all")
    public List<IndustryResponsePayload> getAllIndustries() {
        return industryService.getAll().stream()
                .map(IndustryResponsePayload::new)
                .toList();
    }

    @GetMapping("/{id}")
    public IndustryResponsePayload getIndustryById(@PathVariable("id") Integer id) {
        Industry industry = industryService.getById(id);
        return new IndustryResponsePayload(industry);
    }

    @PutMapping("/save")
    public IndustryResponsePayload saveIndustry(@RequestBody SaveOrUpdateIndustryPayload saveOrUpdateIndustryPayload) {
        Industry industry = saveOrUpdateIndustryPayload.toEntity();
        Industry savedIndustry = industryService.saveOrUpdate(industry);
        return new IndustryResponsePayload(savedIndustry);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCountry(@PathVariable("id") Integer industryId) {
        industryService.deleteById(industryId);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(IndustryNotFoundException.class)
    public ResponseEntity<ProblemDetail> industryNotFound(IndustryNotFoundException e) {
        return ResponseEntity.of(ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND, e.getMessage()
        )).build();
    }

}
