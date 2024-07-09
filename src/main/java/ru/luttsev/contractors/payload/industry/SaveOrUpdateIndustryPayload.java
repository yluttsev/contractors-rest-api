package ru.luttsev.contractors.payload.industry;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO запроса на сохранение или обновление промышленности
 * @author Yuri Luttsev
 */
@Schema(name = "Запрос на сохранение или обновление промышленности")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SaveOrUpdateIndustryPayload {

    @Schema(description = "ID промышленности")
    private Integer id;

    @Schema(description = "Название промышленности")
    private String name;

}
