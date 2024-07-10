package ru.luttsev.contractors.payload.contractor;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import ru.luttsev.contractors.payload.industry.IndustryResponsePayload;

/**
 * DTO фильтров поиска контрагента
 * @author Yuri Luttsev
 */
@Schema(name = "Фильтры поиска контрагентов")
@Data
@Builder
public class ContractorFiltersPayload {

    @Schema(description = "ID контрагента", maxLength = 12, example = "76sOw8bMv4bR")
    private String id;

    @Schema(description = "ID родителя контрагента", maxLength = 12, example = "Plb5ZIwDT66L")
    @JsonProperty("parent_id")
    private String parentId;

    @Schema(description = "Имя контрагента", example = "Московская биржа ММВБ-РТС")
    private String name;

    @Schema(description = "Полное имя контрагента", example = "Публичное акционерное общество «Московская биржа ММВБ-РТС»")
    @JsonProperty("name_full")
    private String fullName;

    @Schema(description = "ИНН контрагента", example = "7702077840")
    private String inn;

    @Schema(description = "ОГРН контрагента", example = "1027739387411")
    private String ogrn;

    @Schema(description = "Название страны", example = "Российская Федерация")
    @JsonProperty("country")
    private String countryName;

    @Schema(description = "Промышленность", exampleClasses = {IndustryResponsePayload.class})
    private IndustryResponsePayload industry;

    @Schema(description = "Форма организации", example = "Казенное учреждение")
    @JsonProperty("org_form")
    private String orgFormName;

}
