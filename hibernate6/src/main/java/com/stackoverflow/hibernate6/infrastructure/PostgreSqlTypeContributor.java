package com.stackoverflow.hibernate6.infrastructure;

import com.stackoverflow.hibernate6.types.SqlXmlType;
import org.hibernate.boot.model.TypeContributions;
import org.hibernate.boot.model.TypeContributor;
import org.hibernate.service.ServiceRegistry;

public class PostgreSqlTypeContributor implements TypeContributor {

    @Override
    public void contribute(TypeContributions typeContributions, ServiceRegistry serviceRegistry) {
        typeContributions.contributeType(new SqlXmlType());
    }
}
