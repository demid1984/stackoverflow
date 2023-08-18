package com.stackoverflow.hibernate5.service;

import com.stackoverflow.hibernate5.model.TestFunctionResult;
import com.stackoverflow.hibernate5.types.SqlXmlType;
import org.hibernate.procedure.ProcedureOutputs;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.sql.SQLException;
import java.sql.SQLXML;

@Service
public class StoreProcedureCaller {

    @PersistenceContext
    private EntityManager entityManager;

    public TestFunctionResult callTestFunction(String id, String xml) throws SQLException {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("test_function")
                .registerStoredProcedureParameter("p_id", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_xml", SqlXmlType.class, ParameterMode.IN)
                .registerStoredProcedureParameter("res_id", String.class, ParameterMode.OUT)
                .registerStoredProcedureParameter("res_code", Integer.class, ParameterMode.OUT)
                .registerStoredProcedureParameter("res_xml", SqlXmlType.class, ParameterMode.OUT)
                .setParameter("p_id", id)
                .setParameter("p_xml", xml)
                ;
        TestFunctionResult testFunctionResult = new TestFunctionResult();
        try {
            query.execute();
            testFunctionResult.setId((String)query.getOutputParameterValue("res_id"));
            testFunctionResult.setCode((Integer) query.getOutputParameterValue("res_code"));
            testFunctionResult.setXml(((SQLXML) query.getOutputParameterValue("res_xml")).getString());
        } finally {
            query.unwrap(ProcedureOutputs.class).release();
        }
        return testFunctionResult;
    }
}
