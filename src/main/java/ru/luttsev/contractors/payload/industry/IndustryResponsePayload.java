package ru.luttsev.contractors.payload.industry;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO промышленности
 * @author Yuri Luttsev
 */
@Schema(name = "Объект промышленности")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class IndustryResponsePayload {

    @Schema(description = "ID промышленности", example = "1")
    private Integer id;

    @Schema(description = "Название промышленности", example = "Авиастроение")
    private String name;

}
