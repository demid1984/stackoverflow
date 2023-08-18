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
        String xml = """
                <note>
                  <to>Tove</to>
                  <from>Jani</from>
                  <heading>Reminder</heading>
                  <body>Don't forget me this weekend!</body>
                </note>
                """;
        TestFunctionResult result = storeProcedureCaller.callTestFunction(id, xml);
        assertEquals(id, result.getId());
        assertEquals(5, result.getCode());
        assertEquals(xml, result.getXml());
    }


}
