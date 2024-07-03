package ru.luttsev.contractors.payload.industry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.luttsev.contractors.entity.Industry;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SaveIndustryPayload {

    private Integer id;

    private String name;

    public Industry toEntity() {
        return Industry.builder()
                .id(this.id)
                .name(this.name)
                .isActive(true)
                .build();
    }

}
