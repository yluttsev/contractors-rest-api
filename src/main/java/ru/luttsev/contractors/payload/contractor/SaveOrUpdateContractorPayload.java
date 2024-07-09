package ru.luttsev.contractors.payload.contractor;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.luttsev.contractors.payload.country.CountryResponsePayload;
import ru.luttsev.contractors.payload.industry.IndustryResponsePayload;
import ru.luttsev.contractors.payload.orgform.OrgFormResponsePayload;

/**
 * DTO запроса на сохранение или обновление контрагента
 * @author Yuri Luttsev
 */
@Schema(name = "Запрос на сохранение или обновление контрагента")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SaveOrUpdateContractorPayload {

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

    @Schema(description = "Страна", exampleClasses = {CountryResponsePayload.class})
    private CountryResponsePayload country;

    @Schema(description = "Промышленность", exampleClasses = {IndustryResponsePayload.class})
    private IndustryResponsePayload industry;

    @Schema(description = "Форма организации", exampleClasses = {OrgFormResponsePayload.class})
    @JsonProperty("org_form")
    private OrgFormResponsePayload orgForm;

}
