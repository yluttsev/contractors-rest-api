package ru.luttsev.contractors.payload.orgform;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO формы организации
 * @author Yuri Luttsev
 */
@Schema(name = "Объект формы организации")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrgFormResponsePayload {

    @Schema(description = "ID формы организации", example = "2")
    private Integer id;

    @Schema(description = "Название формы организации", example = "Автономная некоммерческая организация")
    private String name;

}
