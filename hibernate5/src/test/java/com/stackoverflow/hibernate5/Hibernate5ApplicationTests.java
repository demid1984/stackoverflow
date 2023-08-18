package com.stackoverflow.hibernate5;

import com.stackoverflow.hibernate5.model.TestFunctionResult;
import com.stackoverflow.hibernate5.service.StoreProcedureCaller;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@SpringBootTest
class Hibernate5ApplicationTests {

    static final PostgreSQLContainer<?> sqlContainer = new PostgreSQLContainer<>(
            DockerImageName.parse("postgres")
                    .withTag("11.18-alpine")
                    .asCompatibleSubstituteFor("postgres")
    );

    static {
        sqlContainer.start();
    }

    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", sqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", sqlContainer::getUsername);
        registry.add("spring.datasource.password", sqlContainer::getPassword);
    }

    @Autowired
    StoreProcedureCaller storeProcedureCaller;

    @Test
    void contextLoads() {
        String id = UUID.randomUUID().toString();
        TestFunctionResult result = storeProcedureCaller.callTestFunction(id);
        assertEquals(id, result.getId());
        assertEquals(5, result.getCode());
    }

}
