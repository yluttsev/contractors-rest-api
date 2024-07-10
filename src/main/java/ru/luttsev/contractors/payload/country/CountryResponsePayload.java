package ru.luttsev.contractors.payload.country;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO страны
 * @author Yuri Luttsev
 */
@Schema(name = "Объект страны")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CountryResponsePayload {

    @Schema(description = "ID страны", example = "RUS")
    private String id;

    @Schema(description = "Название страны", example = "Российская Федерация")
    private String name;

}
