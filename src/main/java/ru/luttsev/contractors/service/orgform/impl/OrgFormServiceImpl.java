package ru.luttsev.contractors.service.orgform.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.luttsev.contractors.entity.OrgForm;
import ru.luttsev.contractors.exception.OrgFormNotFoundException;
import ru.luttsev.contractors.repository.OrgFormRepository;
import ru.luttsev.contractors.service.orgform.OrgFormService;

import java.util.List;

/**
 * Сервис для работы с объектами форм организаций
 * @author Yuri Luttsev
 */
@Service
@RequiredArgsConstructor
public class OrgFormServiceImpl implements OrgFormService {

    private final OrgFormRepository orgFormRepository;

    /**
     * Получение всех объектов форм организиций
     * @return {@link OrgForm сущность формы организации}
     */
    @Override
    public List<OrgForm> getAll() {
        return orgFormRepository.findAll();
    }

    /**
     * Получение объекта формы организации по ID
     * @param id ID объекта формы организации
     * @return {@link OrgForm сущность формы организации}
     */
    @Override
    public OrgForm getById(Integer id) {
        return orgFormRepository.findById(id).orElseThrow(
                () -> new OrgFormNotFoundException(id)
        );
    }

    /**
     * Сохранение или обновление объекта формы организации
     * @param orgForm {@link OrgForm сущность объекта формы организации}
     * @return сохраненный или обновленный {@link OrgForm объект формы организации}
     */
    @Override
    @Transactional
    public OrgForm saveOrUpdate(OrgForm orgForm) {
        return orgFormRepository.save(orgForm);
    }

    /**
     * Удаление объекта формы организации по ID
     * @param id ID объекта формы организации
     */
    @Override
    @Transactional
    public void deleteById(Integer id) {
        if (orgFormRepository.existsById(id)) {
            orgFormRepository.deleteById(id);
            return;
        }
        throw new OrgFormNotFoundException(id);
    }

}
