package ru.luttsev.contractors.service.contractor;

import ru.luttsev.contractors.entity.Contractor;
import ru.luttsev.contractors.payload.contractor.ContractorFiltersPayload;
import ru.luttsev.contractors.payload.contractor.ContractorsPagePayload;
import ru.luttsev.contractors.service.CrudService;

public interface ContractorService extends CrudService<Contractor, String> {

    ContractorsPagePayload getByFilters(ContractorFiltersPayload filters, int page, int contentSize);

    ContractorsPagePayload getByFiltersJdbc(ContractorFiltersPayload filters, int page, int contentSize);

}
