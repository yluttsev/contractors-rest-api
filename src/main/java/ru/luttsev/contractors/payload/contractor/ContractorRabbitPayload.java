package ru.luttsev.contractors.payload.contractor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContractorRabbitPayload {

    private String contractorId;

    private String name;

    private String inn;

}
