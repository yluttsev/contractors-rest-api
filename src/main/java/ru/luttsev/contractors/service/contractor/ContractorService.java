package ru.luttsev.contractors.service.contractor;

import org.springframework.security.core.userdetails.UserDetails;
import ru.luttsev.contractors.entity.Contractor;
import ru.luttsev.contractors.payload.contractor.ContractorFiltersPayload;
import ru.luttsev.contractors.payload.contractor.ContractorsPagePayload;
import ru.luttsev.contractors.service.CrudService;

/**
 * Базовый интерфейс сервиса для работы с контрагентами
 *
 * @author Yuri Luttsev
 */
public interface ContractorService extends CrudService<Contractor, String> {

    /**
     * Поиск контрагентов по фильтрам
     *
     * @param filters     {@link ContractorFiltersPayload фильтры поиска} контрагентов
     * @param page        номер страницы
     * @param contentSize количество элементов на странице
     * @return {@link ContractorsPagePayload страница с контрагентами}
     */
    ContractorsPagePayload getByFilters(ContractorFiltersPayload filters, int page, int contentSize);

    /**
     * Поиск контрагентов по фильтрам с помощью JDBC
     *
     * @param filters     {@link ContractorFiltersPayload фильтры поиска} контрагентов
     * @param page        номер страницы
     * @param contentSize количество элементов на странице
     * @return {@link ContractorsPagePayload страница с контрагентами}
     */
    ContractorsPagePayload getByFiltersJdbc(ContractorFiltersPayload filters, int page, int contentSize);

    Contractor setMainBorrower(String contractorId, boolean isMainBorrower);

    ContractorsPagePayload getByFiltersJdbcWithCheckRole(ContractorFiltersPayload filters, int page, int contentSize, UserDetails userDetails);

    ContractorsPagePayload getByFiltersWithCheckRole(ContractorFiltersPayload filters, int page, int contentSize, UserDetails userDetails);

}
