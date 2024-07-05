package ru.luttsev.contractors.payload.industry;

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

    public IndustryResponsePayload(Industry industry) {
        this.id = industry.getId();
        this.name = industry.getName();
    }

    public Industry toEntity() {
        return Industry.builder()
                .id(this.id)
                .name(this.name)
                .isActive(true)
                .build();
    }

}
