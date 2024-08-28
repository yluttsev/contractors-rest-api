package ru.luttsev.contractors.payload.rabbitmq;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Объект сообщения главного заемщика
 *
 * @author Yuri Luttsev
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MainBorrowerRabbitMessage {

    private String contractorId;

    private Boolean isMain;

}
