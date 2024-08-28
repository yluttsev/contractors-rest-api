package ru.luttsev.contractors.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.luttsev.contractors.payload.rabbitmq.MainBorrowerRabbitMessage;
import ru.luttsev.contractors.service.contractor.ContractorService;

import java.io.IOException;

/**
 * Слушатель сообщений из очереди main-borrower
 *
 * @author Yuri Lutstev
 */
@Component
@RequiredArgsConstructor
public class MainBorrowerListener {

    private final ObjectMapper objectMapper;

    private final ContractorService contractorService;

    @RabbitListener(queues = "main_borrower_queue")
    public void getMainBorrowerMessages(Message message) throws IOException {
        MainBorrowerRabbitMessage rabbitMessage = objectMapper.readValue(message.getBody(), MainBorrowerRabbitMessage.class);
        contractorService.setMainBorrower(rabbitMessage.getContractorId(), rabbitMessage.getIsMain());
    }

}
