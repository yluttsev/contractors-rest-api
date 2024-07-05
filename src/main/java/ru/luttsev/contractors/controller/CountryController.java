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
import ru.luttsev.contractors.entity.Country;
import ru.luttsev.contractors.exception.CountryNotFoundException;
import ru.luttsev.contractors.payload.country.CountryResponsePayload;
import ru.luttsev.contractors.payload.country.SaveOrUpdateCountryPayload;
import ru.luttsev.contractors.service.country.CountryService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/country")
public class CountryController {

    private final CountryService countryService;

    @GetMapping("/all")
    public List<CountryResponsePayload> getAllCountries() {
        return countryService.getAll().stream()
                .map(CountryResponsePayload::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CountryResponsePayload getCountryById(@PathVariable("id") String countryId) {
        Country country = countryService.getById(countryId);
        return new CountryResponsePayload(country);
    }

    @PutMapping("/save")
    public CountryResponsePayload saveOrUpdateCountry(@RequestBody SaveOrUpdateCountryPayload saveOrUpdateCountryPayload) {
        Country country = saveOrUpdateCountryPayload.toEntity();
        Country savedCountry = countryService.saveOrUpdate(country);
        return new CountryResponsePayload(savedCountry);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCountry(@PathVariable("id") String countryId) {
        countryService.deleteById(countryId);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(value = CountryNotFoundException.class)
    public ResponseEntity<ProblemDetail> countryNotFound(CountryNotFoundException e) {
        return ResponseEntity.of(ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND, e.getMessage()
        )).build();
    }

}
