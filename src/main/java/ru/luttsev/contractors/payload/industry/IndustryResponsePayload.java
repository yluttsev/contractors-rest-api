package ru.luttsev.contractors.payload.industry;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.luttsev.contractors.entity.Industry;

/**
 * DTO промышленности
 * @author Yuri Luttsev
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class IndustryResponsePayload {

    private Integer id;

    private String name;

    /**
     * Преобразование сущности в DTO
     * @param industry {@link Industry сущность промышленности}
     */
    public IndustryResponsePayload(Industry industry) {
        this.id = industry.getId();
        this.name = industry.getName();
    }

    /**
     * Преобразование DTO в сущность
     * @return {@link Industry сущность промышленности}
     */
    public Industry toEntity() {
        return Industry.builder()
                .id(this.id)
                .name(this.name)
                .isActive(true)
                .build();
    }

}
