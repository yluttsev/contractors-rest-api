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
import ru.luttsev.contractors.payload.industry.IndustryPayload;
import ru.luttsev.contractors.payload.industry.SaveIndustryPayload;
import ru.luttsev.contractors.service.IndustryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/industry")
public class IndustryController {

    private final IndustryService industryService;

    @GetMapping("/all")
    public List<IndustryPayload> getAllIndustries() {
        return industryService.getAll().stream()
                .map(IndustryPayload::new)
                .toList();
    }

    @GetMapping("/{id}")
    public IndustryPayload getIndustryById(@PathVariable("id") Integer id) {
        Industry industry = industryService.getById(id);
        return new IndustryPayload(industry);
    }

    @PutMapping("/save")
    public IndustryPayload saveIndustry(@RequestBody SaveIndustryPayload saveIndustryPayload) {
        Industry industry = saveIndustryPayload.toEntity();
        Industry savedIndustry = industryService.save(industry);
        return new IndustryPayload(savedIndustry);
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
