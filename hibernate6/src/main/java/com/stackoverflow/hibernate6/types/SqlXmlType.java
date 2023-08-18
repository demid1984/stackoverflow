package com.stackoverflow.hibernate6.types;

import io.hypersistence.utils.hibernate.type.ImmutableType;
import io.hypersistence.utils.hibernate.type.util.Configuration;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.query.sqm.SqmExpressible;
import org.hibernate.type.ProcedureParameterExtractionAware;
import org.hibernate.type.descriptor.jdbc.JdbcType;
import org.hibernate.type.descriptor.jdbc.XmlAsStringJdbcType;
import org.hibernate.type.spi.TypeBootstrapContext;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Types;

public class SqlXmlType extends ImmutableType<Xml> implements ProcedureParameterExtractionAware<Xml> {

    public SqlXmlType() {
        super(Xml.class);
    }

    public SqlXmlType(TypeBootstrapContext typeBootstrapContext) {
        super(Xml.class, new Configuration(typeBootstrapContext.getConfigurationSettings()));
    }

    @Override
    protected Xml get(ResultSet rs, int i, SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws SQLException {
        SQLXML xml = rs.getSQLXML(i);
        return rs.wasNull() ? null : new Xml(xml.getString());
    }

    @Override
    protected void set(PreparedStatement st, Xml value, int index, SharedSessionContractImplementor sharedSessionContractImplementor) throws SQLException {
        if (value == null) {
            st.setNull(index, Types.OTHER);
        } else {
            st.setObject(index, value.getXml(), Types.SQLXML);
        }
    }

    @Override
    public int getSqlType() {
        return Types.SQLXML;
    }

    @Override
    public Xml fromStringValue(CharSequence charSequence) throws HibernateException {
        return charSequence == null ? null : new Xml(String.valueOf(charSequence));
    }

    @Override
    public boolean canDoExtraction() {
        return true;
    }

    @Override
    public JdbcType getJdbcType() {
        return XmlAsStringJdbcType.VARCHAR_INSTANCE;
    }

    @Override
    public Xml extract(CallableStatement callableStatement, int i, SharedSessionContractImplementor sharedSessionContractImplementor) throws SQLException {
        SQLXML sqlxml = callableStatement.getSQLXML(i);
        return sqlxml == null ? null : new Xml(sqlxml.getString());
    }

    @Override
    public Xml extract(CallableStatement callableStatement, String name, SharedSessionContractImplementor sharedSessionContractImplementor) throws SQLException {
        SQLXML sqlxml = callableStatement.getSQLXML(name);
        return sqlxml == null ? null : new Xml(sqlxml.getString());
    }

    @Override
    public Class<Xml> getBindableJavaType() {
        return Xml.class;
    }

    @Override
    public SqmExpressible<Xml> resolveExpressible(SessionFactoryImplementor sessionFactoryImplementor) {
        return null;
    }

}
