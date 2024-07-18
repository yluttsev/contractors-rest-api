package ru.luttsev.contractors.payload.contractor;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SetMainBorrowerPayload {

    @JsonProperty("contractor_id")
    private String contractorId;

    @JsonProperty("main_borrower")
    private boolean mainBorrower;

}
