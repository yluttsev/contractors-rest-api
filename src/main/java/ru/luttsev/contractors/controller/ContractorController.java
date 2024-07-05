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
import ru.luttsev.contractors.entity.Contractor;
import ru.luttsev.contractors.exception.ContractorNotFoundException;
import ru.luttsev.contractors.payload.contractor.ContractorResponsePayload;
import ru.luttsev.contractors.payload.contractor.SaveOrUpdateContractorPayload;
import ru.luttsev.contractors.service.EntityService;

import java.util.List;

@RestController
@RequestMapping("/contractor")
@RequiredArgsConstructor
public class ContractorController {

    private final EntityService<Contractor, String> contractorService;

    @GetMapping("/all")
    public List<ContractorResponsePayload> getAllContractors() {
        return contractorService.getAll().stream()
                .map(ContractorResponsePayload::new)
                .toList();
    }

    @GetMapping("/{id}")
    public ContractorResponsePayload getContractorById(@PathVariable("id") String contractorId) {
        return new ContractorResponsePayload(contractorService.getById(contractorId));
    }

    @PutMapping("/save")
    public ContractorResponsePayload saveOrUpdateContractor(@RequestBody SaveOrUpdateContractorPayload contractorPayload) {
        return new ContractorResponsePayload(contractorService.saveOrUpdate(contractorPayload.toEntity()));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteContractorById(@PathVariable("id") String contractorId) {
        contractorService.deleteById(contractorId);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(ContractorNotFoundException.class)
    public ProblemDetail contractorNotFound(ContractorNotFoundException e) {
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND, e.getMessage()
        );
    }

}
