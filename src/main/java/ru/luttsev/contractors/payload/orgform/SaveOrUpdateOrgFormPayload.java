package ru.luttsev.contractors.payload.orgform;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.luttsev.contractors.entity.OrgForm;

/**
 * DTO запроса на сохранение или обновление формы организации
 * @author Yuri Luttsev
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SaveOrUpdateOrgFormPayload {

    private Integer id;

    private String name;

    public OrgForm toEntity() {
        return OrgForm.builder()
                .id(this.id)
                .name(this.name)
                .isActive(true)
                .build();
    }

}
