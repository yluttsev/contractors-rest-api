package ru.luttsev.contractors.payload.contractor;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * DTO страницы с информацией о контрагентах
 * @author Yuri Luttsev
 */
@Schema(name = "Страница найденных по фильтрам контрагентов")
@AllArgsConstructor
@Data
@Builder
public class ContractorsPagePayload {

    @Schema(description = "Номер страницы", example = "1")
    private int page;

    @Schema(description = "Количество элементов на странице", example = "10")
    private int items;

    @Schema(description = "Список контрагентов")
    private List<ContractorResponsePayload> contractors;

}
