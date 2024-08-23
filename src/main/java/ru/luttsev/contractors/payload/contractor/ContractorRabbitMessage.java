package ru.luttsev.contractors.payload.contractor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ContractorRabbitMessage {

    @Setter
    private ContractorRabbitPayload contractor;

    private final LocalDateTime modifyDate = LocalDateTime.now();

}
