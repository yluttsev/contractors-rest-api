package ru.luttsev.contractors.payload.industry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO запроса на сохранение или обновление промышленности
 * @author Yuri Luttsev
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SaveOrUpdateIndustryPayload {

    private Integer id;

    private String name;

}
