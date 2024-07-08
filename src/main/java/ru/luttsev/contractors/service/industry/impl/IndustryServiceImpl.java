package ru.luttsev.contractors.service.industry.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.luttsev.contractors.entity.Industry;
import ru.luttsev.contractors.exception.IndustryNotFoundException;
import ru.luttsev.contractors.repository.IndustryRepository;
import ru.luttsev.contractors.service.industry.IndustryService;

import java.util.List;

/**
 * Сервис для работы с объектами промышленности
 * @author Yuri Luttsev
 */
@Service
@RequiredArgsConstructor
public class IndustryServiceImpl implements IndustryService {

    private final IndustryRepository industryRepository;

    /**
     * Получение всех объектов промышленности
     * @return список {@link Industry сущностей промышленности}
     */
    @Override
    public List<Industry> getAll() {
        return industryRepository.findAll();
    }

    /**
     * Получение объекта промышленности по ID
     * @param id ID промышленности
     * @return {@link Industry сущность промышленности}
     */
    @Override
    public Industry getById(Integer id) {
        return industryRepository.findById(id).orElseThrow(
                () -> new IndustryNotFoundException(id)
        );
    }

    /**
     * Сохранение или обновление объекта промышленности
     * @param industry {@link Industry сущность промышленности}
     * @return сохраненный или обновленный {@link Industry объект промышленности}
     */
    @Override
    @Transactional
    public Industry saveOrUpdate(Industry industry) {
        return industryRepository.save(industry);
    }

    /**
     * Удаление объекта промышленности по ID
     * @param id ID объекта промышленности
     */
    @Override
    @Transactional
    public void deleteById(Integer id) {
        if (industryRepository.existsById(id)) {
            industryRepository.deleteById(id);
            return;
        }
        throw new IndustryNotFoundException(id);
    }

}
