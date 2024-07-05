package ru.luttsev.contractors.payload.contractor;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.luttsev.contractors.entity.Contractor;
import ru.luttsev.contractors.payload.country.CountryResponsePayload;
import ru.luttsev.contractors.payload.industry.IndustryResponsePayload;
import ru.luttsev.contractors.payload.orgform.OrgFormResponsePayload;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SaveOrUpdateContractorPayload {

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

    public Contractor toEntity() {
        return Contractor.builder()
                .id(this.id)
                .parentId(this.parentId)
                .name(this.name)
                .fullName(this.fullName)
                .inn(this.inn)
                .ogrn(this.ogrn)
                .country(this.country.toEntity())
                .industry(this.industry.toEntity())
                .orgForm(this.orgForm.toEntity())
                .isActive(true)
                .build();
    }

}
