package com.stackoverflow.hibernate6.service;

import com.stackoverflow.hibernate6.model.TestFunctionResult;
import com.stackoverflow.hibernate6.types.Xml;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import org.hibernate.procedure.ProcedureCall;
import org.springframework.stereotype.Service;

@Service
public class StoreProcedureCaller {

    @PersistenceContext
    private EntityManager entityManager;

    public TestFunctionResult callTestFunction(String id, String xml) {
        TestFunctionResult testFunctionResult = new TestFunctionResult();
        try (ProcedureCall procedureCall = entityManager.createStoredProcedureQuery("test_function").unwrap(ProcedureCall.class)) {
            procedureCall.registerStoredProcedureParameter("p_id", String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("p_xml", Xml.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("res_id", String.class, ParameterMode.OUT)
                    .registerStoredProcedureParameter("res_code", Integer.class, ParameterMode.OUT)
                    .registerStoredProcedureParameter("res_xml", Xml.class, ParameterMode.OUT)
                    .setParameter("p_id", id)
                    .setParameter("p_xml", new Xml(xml));

            testFunctionResult.setId((String) procedureCall.getOutputParameterValue("res_id"));
            testFunctionResult.setCode((Integer) procedureCall.getOutputParameterValue("res_code"));
            testFunctionResult.setXml(((Xml) procedureCall.getOutputParameterValue("res_xml")).getXml());

        }
        return testFunctionResult;
    }
}
