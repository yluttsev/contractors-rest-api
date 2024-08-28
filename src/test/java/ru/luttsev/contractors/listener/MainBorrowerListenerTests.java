package ru.luttsev.contractors.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import ru.luttsev.contractors.PostgresContainer;
import ru.luttsev.contractors.payload.rabbitmq.MainBorrowerRabbitMessage;
import ru.luttsev.contractors.repository.ContractorRepository;

import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Import(PostgresContainer.class)
@Testcontainers
class MainBorrowerListenerTests {

    @Container
    static RabbitMQContainer rabbitMQContainer = new RabbitMQContainer(DockerImageName.parse("rabbitmq:4.0-rc-management-alpine"));

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry properties) {
        properties.add("spring.rabbitmq.host", rabbitMQContainer::getHost);
        properties.add("spring.rabbitmq.port", rabbitMQContainer::getAmqpPort);
        properties.add("spring.rabbitmq.username", rabbitMQContainer::getAdminUsername);
        properties.add("spring.rabbitmq.password", rabbitMQContainer::getAdminPassword);
    }

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ContractorRepository contractorRepository;

    @Value("${rabbitmq.main-borrower-exchange}")
    private String mainBorrowerExchangeName;

    @Value("${rabbitmq.main-borrower-queue}")
    private String mainBorrowerQueueName;

    @Test
    @Sql("/sql/contractors_test.sql")
    void testSetActiveMainBorrowerToContractorFromQueue() throws JsonProcessingException {
        MainBorrowerRabbitMessage message = MainBorrowerRabbitMessage.builder()
                .contractorId("1")
                .isMain(true)
                .build();
        rabbitTemplate.convertAndSend(mainBorrowerExchangeName, mainBorrowerQueueName, objectMapper.writeValueAsString(message));

        await().atMost(10, TimeUnit.SECONDS).untilAsserted(
                () -> assertTrue(contractorRepository.findById("1").get().isActiveMainBorrower())
        );
    }

}
