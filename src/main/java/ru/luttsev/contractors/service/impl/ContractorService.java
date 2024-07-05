package ru.luttsev.contractors.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.luttsev.contractors.entity.Contractor;
import ru.luttsev.contractors.exception.ContractorNotFoundException;
import ru.luttsev.contractors.repository.ContractorRepository;
import ru.luttsev.contractors.service.EntityService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContractorService implements EntityService<Contractor, String> {

    private final ContractorRepository contractorRepository;

    @Override
    public List<Contractor> getAll() {
        return contractorRepository.findAll();
    }

    @Override
    public Contractor getById(String id) {
        return contractorRepository.findById(id).orElseThrow(
                () -> new ContractorNotFoundException(id)
        );
    }

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

    @Override
    @Transactional
    public void deleteById(String id) {
        if (contractorRepository.existsById(id)) {
            contractorRepository.deleteById(id);
            return;
        }
        throw new ContractorNotFoundException(id);
    }

}
