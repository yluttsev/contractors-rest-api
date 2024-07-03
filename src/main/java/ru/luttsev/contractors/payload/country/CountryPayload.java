package ru.luttsev.contractors.payload.country;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.luttsev.contractors.entity.Country;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CountryPayload {

    private String id;

    private String name;

    @JsonProperty(value = "is_active")
    private Boolean isActive;

    public CountryPayload(Country country) {
        this.id = country.getId();
        this.name = country.getName();
        this.isActive = country.getIsActive();
    }

    public Country toEntity() {
        return Country.builder()
                .id(this.id)
                .name(this.name)
                .isActive(this.isActive)
                .build();
    }

}
