package ru.luttsev.contractors.payload.country;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.luttsev.contractors.entity.Country;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CountryResponsePayload {

    private String id;

    private String name;

    public CountryResponsePayload(Country country) {
        this.id = country.getId();
        this.name = country.getName();
    }

    public Country toEntity() {
        return Country.builder()
                .id(this.id)
                .name(this.name)
                .isActive(true)
                .build();
    }

}
