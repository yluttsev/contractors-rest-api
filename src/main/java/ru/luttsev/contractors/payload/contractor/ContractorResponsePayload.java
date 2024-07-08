package ru.luttsev.contractors.payload.contractor;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.luttsev.contractors.payload.country.CountryResponsePayload;
import ru.luttsev.contractors.payload.industry.IndustryResponsePayload;
import ru.luttsev.contractors.payload.orgform.OrgFormResponsePayload;

/**
 * DTO контаргента
 * @author Yuri Luttsev
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ContractorResponsePayload {

    private String id;

    @JsonProperty("parent_id")
    private String parentId;

    private String name;

    @JsonProperty("name_full")
    private String fullName;

    private String inn;

    private String ogrn;

    private CountryResponsePayload country;

    private IndustryResponsePayload industry;

    @JsonProperty("org_form")
    private OrgFormResponsePayload orgForm;

}
