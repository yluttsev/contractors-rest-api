package ru.luttsev.contractors.service.country.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.luttsev.contractors.entity.Country;
import ru.luttsev.contractors.exception.CountryNotFoundException;
import ru.luttsev.contractors.repository.CountryRepository;
import ru.luttsev.contractors.service.country.CountryService;

import java.util.List;

/**
 * Сервис для работы со странами
 * @author Yuri Luttsev
 */
@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    /**
     * Получение всех стран
     * @return список {@link Country сущностей стран}
     */
    @Override
    public List<Country> getAll() {
        return countryRepository.findAll();
    }

    /**
     * Получение страны по ID
     * @param id ID страны
     * @return {@link Country сущность страны}
     */
    @Override
    public Country getById(String id) {
        return countryRepository.findById(id).orElseThrow(
                () -> new CountryNotFoundException(id)
        );
    }

    /**
     * Сохранение или обновление страны
     * @param country {@link Country сущность страны}
     * @return сохраненная или обновленная {@link Country страна}
     */
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

    /**
     * Удаление страны по ID
     * @param id ID страны
     */
    @Override
    @Transactional
    public void deleteById(String id) {
        if (countryRepository.existsById(id)) {
            countryRepository.deleteById(id);
            return;
        }
        throw new CountryNotFoundException(id);
    }

}
