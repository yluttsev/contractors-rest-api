package ru.luttsev.contractors.payload.orgform;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

}
