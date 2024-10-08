package ru.luttsev.contractors.service.security;

import org.springframework.security.core.userdetails.UserDetails;
import ru.luttsev.contractors.payload.contractor.ContractorFiltersPayload;

public interface SecurityService {

    void updateFiltersWithRole(ContractorFiltersPayload filters, UserDetails userDetails);

}
