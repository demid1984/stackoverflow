package com.stackoverflow.hibernate5.types;

import io.hypersistence.utils.hibernate.type.ImmutableType;
import org.hibernate.engine.spi.SharedSessionContractImplementor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Types;

public class SqlXmlType extends ImmutableType<String> {

    private final int[] sqlTypesSupported = new int[] { Types.SQLXML };

    public SqlXmlType() {
        super(String.class);
    }

    @Override
    public int[] sqlTypes() {
        return sqlTypesSupported;
    }

    @Override
    protected String get(ResultSet rs, String[] names, SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws SQLException {
        assert(names.length == 1);
        SQLXML xml = rs.getSQLXML( names[0] );
        return rs.wasNull() ? null : xml.getString();
    }

    @Override
    protected void set(PreparedStatement st, String value, int index, SharedSessionContractImplementor sharedSessionContractImplementor) throws SQLException {
        if (value == null) {
            st.setNull(index, Types.SQLXML);
        } else {
            st.setObject(index, value, Types.SQLXML);
        }
    }

}
