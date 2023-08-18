package com.stackoverflow.hibernate5.service;

import com.stackoverflow.hibernate5.model.TestFunctionResult;
import org.hibernate.procedure.ProcedureOutputs;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

@Service
public class StoreProcedureCaller {

    @PersistenceContext
    private EntityManager entityManager;

    public TestFunctionResult callTestFunction(String id) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("test_function")
                .registerStoredProcedureParameter("id", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("res_id", String.class, ParameterMode.OUT)
                .registerStoredProcedureParameter("res_code", Integer.class, ParameterMode.OUT)
                .setParameter("id", id);
        TestFunctionResult testFunctionResult = new TestFunctionResult();
        try {
            query.execute();
            testFunctionResult.setId((String)query.getOutputParameterValue("res_id"));
            testFunctionResult.setCode((Integer) query.getOutputParameterValue("res_code"));
        } finally {
            query.unwrap(ProcedureOutputs.class).release();
        }
        return testFunctionResult;
    }
}
