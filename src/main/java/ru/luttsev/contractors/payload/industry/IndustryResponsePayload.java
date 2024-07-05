package ru.luttsev.contractors.payload.industry;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.luttsev.contractors.entity.Industry;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class IndustryResponsePayload {

    private Integer id;

    private String name;

    @JsonProperty("is_active")
    private Boolean isActive;

    public IndustryResponsePayload(Industry industry) {
        this.id = industry.getId();
        this.name = industry.getName();
        this.isActive = industry.getIsActive();
    }

}
