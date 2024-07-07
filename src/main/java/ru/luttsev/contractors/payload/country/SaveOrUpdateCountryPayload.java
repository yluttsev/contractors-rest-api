package ru.luttsev.contractors.payload.country;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.luttsev.contractors.entity.Country;

/**
 * DTO запроса на сохранение или обновление страны
 * @author Yuri Luttsev
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SaveOrUpdateCountryPayload {

    private String id;

    private String name;

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
