package ru.luttsev.contractors.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.luttsev.contractors.entity.Country;
import ru.luttsev.contractors.exception.CountryNotFoundException;
import ru.luttsev.contractors.repository.CountryRepository;
import ru.luttsev.contractors.service.CountryService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    @Override
    public List<Country> getAll() {
        return countryRepository.findAll();
    }

    @Override
    public Country getById(String id) {
        return countryRepository.findById(id).orElseThrow(
                () -> new CountryNotFoundException(id)
        );
    }

    @Override
    @Transactional
    public Country saveOrUpdate(Country country) {
        if (country.getId() != null && countryRepository.existsById(country.getId())) {
            Country countryEntity = getById(country.getId());
            countryEntity.setId(country.getId());
            countryEntity.setName(country.getName());
            return countryRepository.save(countryEntity);
        }
        return countryRepository.save(country);
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        if (countryRepository.existsById(id)) {
            countryRepository.deleteById(id);
        }
        throw new CountryNotFoundException(id);
    }

}
