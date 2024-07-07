package ru.luttsev.contractors.payload.contractor;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * DTO страницы с информацией о контрагентах
 * @author Yuri Luttsev
 */
@AllArgsConstructor
@Data
public class ContractorsPagePayload {

    private int page;

    private int items;

    private List<ContractorResponsePayload> contractors;

}
