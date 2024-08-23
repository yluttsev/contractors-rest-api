package ru.luttsev.contractors;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(PostgresContainer.class)
class ContractorsRestApiApplicationTests {

    @Test
    void contextLoads() {
    }

}
