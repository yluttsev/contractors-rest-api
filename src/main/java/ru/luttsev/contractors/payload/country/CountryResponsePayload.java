package ru.luttsev.contractors.payload.country;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.luttsev.contractors.entity.Country;

/**
 * DTO страны
 * @author Yuri Luttsev
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CountryResponsePayload {

    private String id;

    private String name;

    /**
     * Преобразование сущности в DTO
     * @param country {@link Country сущность страны}
     */
    public CountryResponsePayload(Country country) {
        this.id = country.getId();
        this.name = country.getName();
    }

    /**
     * Преобразование DTO в сущность
     * @return {@link Country сущность страны}
     */
    public Country toEntity() {
        return Country.builder()
                .id(this.id)
                .name(this.name)
                .isActive(true)
                .build();
    }

}
