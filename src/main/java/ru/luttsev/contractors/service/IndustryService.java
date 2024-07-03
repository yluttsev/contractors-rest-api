package ru.luttsev.contractors.service;

import ru.luttsev.contractors.entity.Industry;

import java.util.List;

public interface IndustryService {

    List<Industry> getAll();

    Industry getById(Integer id);

    Industry save(Industry industry);

    void deleteById(Integer id);

}
