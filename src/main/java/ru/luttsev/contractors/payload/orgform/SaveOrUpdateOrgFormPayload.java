package ru.luttsev.contractors.payload.orgform;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO запроса на сохранение или обновление формы организации
 * @author Yuri Luttsev
 */
@Schema(name = "Запрос на сохранение или обновление формы организации")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SaveOrUpdateOrgFormPayload {

    @Schema(description = "ID формы организации", example = "2")
    private Integer id;

    @Schema(description = "Название формы организации", example = "Автономная некоммерческая организация")
    private String name;

}
