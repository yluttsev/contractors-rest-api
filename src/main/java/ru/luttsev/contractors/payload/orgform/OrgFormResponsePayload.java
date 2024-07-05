package ru.luttsev.contractors.payload.orgform;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.luttsev.contractors.entity.OrgForm;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrgFormResponsePayload {

    private Integer id;

    private String name;

    public OrgFormResponsePayload(OrgForm orgForm) {
        this.id = orgForm.getId();
        this.name = orgForm.getName();
    }

    public OrgForm toEntity() {
        return OrgForm.builder()
                .id(this.id)
                .name(this.name)
                .isActive(true)
                .build();
    }

}
