package ru.luttsev.contractors.service.security.impl;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.luttsev.contractors.payload.contractor.ContractorFiltersPayload;
import ru.luttsev.contractors.service.security.SecurityService;

import java.util.Collection;
import java.util.Optional;

@Service
public class SecurityServiceImpl implements SecurityService {

    private boolean checkRole(String role, Collection<? extends GrantedAuthority> roles) {
        return roles.contains(new SimpleGrantedAuthority(role));
    }

    @Override
    public ContractorFiltersPayload updateFiltersWithRole(ContractorFiltersPayload filters, UserDetails userDetails) {
        Collection<? extends GrantedAuthority> roles = userDetails.getAuthorities();
        if (!this.checkRole("CONTRACTOR_SUPERUSER", roles) || !this.checkRole("SUPERUSER", roles)) {
            if (Optional.ofNullable(filters.getCountryName()).isEmpty()) {
                filters.setCountryName("Российская Федерация");
            } else {
                if (!filters.getCountryName().equals("Российская Федерация")) {
                    filters.setCountryName("null");
                }
            }
        }
        return filters;
    }

}
