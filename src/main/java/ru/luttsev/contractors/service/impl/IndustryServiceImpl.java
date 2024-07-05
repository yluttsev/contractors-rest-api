package ru.luttsev.contractors.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.luttsev.contractors.entity.Industry;
import ru.luttsev.contractors.exception.IndustryNotFoundException;
import ru.luttsev.contractors.repository.IndustryRepository;
import ru.luttsev.contractors.service.EntityService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IndustryServiceImpl implements EntityService<Industry, Integer> {

    private final IndustryRepository industryRepository;

    @Override
    public List<Industry> getAll() {
        return industryRepository.findAll();
    }

    @Override
    public Industry getById(Integer id) {
        return industryRepository.findById(id).orElseThrow(
                () -> new IndustryNotFoundException(id)
        );
    }

    @Override
    @Transactional
    public Industry saveOrUpdate(Industry industry) {
        if (industry.getId() != null && industryRepository.existsById(industry.getId())) {
            Industry industryEntity = getById(industry.getId());
            industryEntity.setId(industry.getId());
            industryEntity.setName(industry.getName());
            return industryRepository.save(industryEntity);
        }
        return industryRepository.save(industry);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        if (industryRepository.existsById(id)) {
            industryRepository.deleteById(id);
        }
        throw new IndustryNotFoundException(id);
    }

}
