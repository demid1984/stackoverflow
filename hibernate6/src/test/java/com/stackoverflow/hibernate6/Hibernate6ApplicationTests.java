package com.stackoverflow.hibernate6;

import com.stackoverflow.hibernate6.model.TestFunctionResult;
import com.stackoverflow.hibernate6.service.StoreProcedureCaller;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ContextConfiguration(classes = {TestHibernate6Configuration.class})
@SpringBootTest
class Hibernate6ApplicationTests {

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
