package ru.luttsev.contractors.service.orgform.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.luttsev.contractors.entity.OrgForm;
import ru.luttsev.contractors.exception.OrgFormNotFoundException;
import ru.luttsev.contractors.repository.OrgFormRepository;
import ru.luttsev.contractors.service.orgform.OrgFormService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrgFormServiceImpl implements OrgFormService {

    private final OrgFormRepository orgFormRepository;

    @Override
    public List<OrgForm> getAll() {
        return orgFormRepository.findAll();
    }

    @Override
    public OrgForm getById(Integer id) {
        return orgFormRepository.findById(id).orElseThrow(
                () -> new OrgFormNotFoundException(id)
        );
    }

    @Override
    @Transactional
    public OrgForm saveOrUpdate(OrgForm orgForm) {
        if (orgForm.getId() != null && orgFormRepository.existsById(orgForm.getId())) {
            OrgForm orgFormEntity = getById(orgForm.getId());
            orgFormEntity.setId(orgForm.getId());
            orgFormEntity.setName(orgForm.getName());
            return orgFormRepository.save(orgFormEntity);
        }
        return orgFormRepository.save(orgForm);
    }

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
