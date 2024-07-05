package ru.luttsev.contractors.service;

import ru.luttsev.contractors.entity.Country;

import java.util.List;

public interface CountryService {

    List<Country> getAll();

    Country getById(String id);

    Country saveOrUpdate(Country country);

    void deleteById(String id);

}
