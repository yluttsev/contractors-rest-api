package ru.luttsev.contractors.payload.contractor;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import ru.luttsev.contractors.payload.industry.IndustryResponsePayload;

/**
 * DTO фильтров поиска контрагента
 * @author Yuri Luttsev
 */
@Data
@Builder
public class ContractorFiltersPayload {

    private String id;

    @JsonProperty("parent_id")
    private String parentId;

    private String name;

    @JsonProperty("name_full")
    private String fullName;

    private String inn;

    private String ogrn;

    @JsonProperty("country")
    private String countryName;

    private IndustryResponsePayload industry;

    @JsonProperty("org_form")
    private String orgFormName;

}
