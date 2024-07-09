package ru.luttsev.contractors.payload.country;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO запроса на сохранение или обновление страны
 * @author Yuri Luttsev
 */
@Schema(name = "Запрос на сохранение или обновление страны")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SaveOrUpdateCountryPayload {

    @Schema(description = "ID страны", example = "RUS")
    private String id;

    @Schema(description = "Имя страны", example = "Российская Федерация")
    private String name;

}
