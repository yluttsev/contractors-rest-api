package ru.luttsev.contractors.payload.orgform;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.luttsev.contractors.entity.OrgForm;

/**
 * DTO формы организации
 * @author Yuri Luttsev
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrgFormResponsePayload {

    private Integer id;

    private String name;

    /**
     * Преобразование сущности в DTO
     * @param orgForm {@link OrgForm сущность формы организации}
     */
    public OrgFormResponsePayload(OrgForm orgForm) {
        this.id = orgForm.getId();
        this.name = orgForm.getName();
    }

    /**
     * Проеобразование DTO в сущность
     * @return {@link OrgForm сущность формы организации}
     */
    public OrgForm toEntity() {
        return OrgForm.builder()
                .id(this.id)
                .name(this.name)
                .isActive(true)
                .build();
    }

}
