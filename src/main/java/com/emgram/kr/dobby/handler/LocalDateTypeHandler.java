package com.emgram.kr.dobby.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

@MappedTypes(LocalDate.class)
@MappedJdbcTypes(JdbcType.DATE)
public class LocalDateTypeHandler extends BaseTypeHandler<LocalDate> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, LocalDate date,
        JdbcType jdbcType) throws SQLException {
        preparedStatement.setDate(i, java.sql.Date.valueOf(date));
    }

    @Override
    public LocalDate getNullableResult(ResultSet resultSet, String s) throws SQLException {
        java.sql.Date date = resultSet.getDate(s);
        return (date != null) ? date.toLocalDate() : null;
    }

    @Override
    public LocalDate getNullableResult(ResultSet resultSet, int i) throws SQLException {
        java.sql.Date date = resultSet.getDate(i);
        return (date != null) ? date.toLocalDate() : null;
    }

    @Override
    public LocalDate getNullableResult(CallableStatement callableStatement, int i)
        throws SQLException {
        java.sql.Date date = callableStatement.getDate(i);
        return (date != null) ? date.toLocalDate() : null;
    }
}
