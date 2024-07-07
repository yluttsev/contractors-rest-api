package ru.luttsev.contractors.payload.industry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.luttsev.contractors.entity.Industry;

/**
 * DTO запроса на сохранение или обновление промышленности
 * @author Yuri Luttsev
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SaveOrUpdateIndustryPayload {

    private Integer id;

    private String name;

    public Industry toEntity() {
        return Industry.builder()
                .id(this.id)
                .name(this.name)
                .isActive(true)
                .build();
    }

}
