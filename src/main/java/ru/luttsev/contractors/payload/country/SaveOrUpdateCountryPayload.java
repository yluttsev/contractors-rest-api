package ru.luttsev.contractors.payload.country;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.luttsev.contractors.entity.Country;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SaveOrUpdateCountryPayload {

    private String id;

    private String name;

    public Country toEntity() {
        return Country.builder()
                .id(this.id)
                .name(this.name)
                .isActive(true)
                .build();
    }

}
