package ru.luttsev.contractors.service.contractor.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.luttsev.contractors.entity.Contractor;
import ru.luttsev.contractors.exception.ContractorNotFoundException;
import ru.luttsev.contractors.payload.contractor.ContractorFiltersPayload;
import ru.luttsev.contractors.payload.contractor.ContractorResponsePayload;
import ru.luttsev.contractors.payload.contractor.ContractorSpecification;
import ru.luttsev.contractors.payload.contractor.ContractorsPagePayload;
import ru.luttsev.contractors.repository.ContractorRepository;
import ru.luttsev.contractors.repository.jdbc.ContractorJdbcRepository;
import ru.luttsev.contractors.service.contractor.ContractorService;

import java.util.List;

/**
 * Сервис для работы с контрагентами
 * @author Yuri Luttsev
 */
@Service
@RequiredArgsConstructor
public class ContractorServiceImpl implements ContractorService {

    private final ContractorRepository contractorRepository;
    private final ContractorJdbcRepository contractorJdbcRepository;

    /**
     * Получение всех контрагентов
     * @return список {@link Contractor сущностей контрагентов}
     */
    @Override
    public List<Contractor> getAll() {
        return contractorRepository.findAll();
    }

    /**
     * Получение контрагента по ID
     * @param id ID контрагента
     * @return {@link Contractor сущность контрагента}
     */
    @Override
    public Contractor getById(String id) {
        return contractorRepository.findById(id).orElseThrow(
                () -> new ContractorNotFoundException(id)
        );
    }

    /**
     * Сохранение или обновление контрагента
     * @param contractor {@link Contractor сущность контрагента}
     * @return сохраненый или обновленный {@link Contractor контрагент}
     */
    @Override
    @Transactional
    public Contractor saveOrUpdate(Contractor contractor) {
        if (contractor.getId() != null && contractorRepository.existsById(contractor.getId())) {
            Contractor contractorEntity = getById(contractor.getId());
            contractorEntity.setId(contractor.getId());
            contractorEntity.setParentId(contractor.getParentId());
            contractorEntity.setName(contractor.getName());
            contractorEntity.setFullName(contractor.getFullName());
            contractorEntity.setId(contractor.getInn());
            contractorEntity.setOgrn(contractor.getOgrn());
            contractorEntity.setCountry(contractor.getCountry());
            contractorEntity.setIndustry(contractor.getIndustry());
            contractorEntity.setOrgForm(contractor.getOrgForm());
            contractorEntity.setIsActive(contractor.getIsActive());
            return contractorRepository.save(contractorEntity);
        }
        return contractorRepository.save(contractor);
    }

    /**
     * Удаление контрагента по ID
     * @param id ID контрагента
     */
    @Override
    @Transactional
    public void deleteById(String id) {
        if (contractorRepository.existsById(id)) {
            contractorRepository.deleteById(id);
            return;
        }
        throw new ContractorNotFoundException(id);
    }

    /**
     * Поиск контрагентов по фильтрам
     * @param filters {@link ContractorFiltersPayload фильтры поиска} контрагентов
     * @param page номер страницы
     * @param contentSize количество элементов на странице
     * @return {@link ContractorsPagePayload страница с контрагентами}
     */
    @Override
    public ContractorsPagePayload getByFilters(ContractorFiltersPayload filters, int page, int contentSize) {
        Specification<Contractor> specification = ContractorSpecification.getContractorByFilters(filters);
        Page<Contractor> contractorsPage = contractorRepository.findAll(specification, PageRequest.of(page, contentSize));
        return new ContractorsPagePayload(contractorsPage.getNumber(),
                contractorsPage.getNumberOfElements(),
                contractorsPage.get().map(ContractorResponsePayload::new).toList());
    }

    /**
     * Поиск контрагентов по фильтрам с помощью JDBC
     * @param filters {@link ContractorFiltersPayload фильтры поиска} контрагентов
     * @param page номер страницы
     * @param contentSize количество элементов на странице
     * @return {@link ContractorsPagePayload страница с контрагентами}
     */
    @Override
    public ContractorsPagePayload getByFiltersJdbc(ContractorFiltersPayload filters, int page, int contentSize) {
        Page<Contractor> contractorPage = contractorJdbcRepository.getContractorsByFilters(filters, page, contentSize);
        return new ContractorsPagePayload(page, contractorPage.getNumberOfElements(), contractorPage.get().map(ContractorResponsePayload::new).toList());
    }

}
