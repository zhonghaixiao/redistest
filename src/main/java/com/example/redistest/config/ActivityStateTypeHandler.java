package com.example.redistest.config;

import com.example.redistest.dao.entity.ActivityState;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.sql.*;

@MappedTypes(DateTime.class)
public class ActivityStateTypeHandler implements TypeHandler {

    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        if (parameter != null){
            ActivityState state = (ActivityState) parameter;
            switch (state){
                case NOT_STARTED:
                    preparedStatement.setString(i, "未开始");
                    break;
                case IN_PROGRESS:
                    preparedStatement.setString(i, "正在进行");
                    break;
                case END:
                    preparedStatement.setString(i, "已结束");
                    break;
            }
        }
    }

    @Override
    public Object getResult(ResultSet resultSet, String columnName) throws SQLException {
        String state = resultSet.getString(columnName);
        return getEnumByState(state);
    }

    @Override
    public Object getResult(ResultSet resultSet, int columnIndex) throws SQLException {
        String state = resultSet.getString(columnIndex);
        return getEnumByState(state);
    }

    private Object getEnumByState(String state) {
        switch (state){
            case "未开始":
                return ActivityState.NOT_STARTED;
            case "正在进行":
                return ActivityState.IN_PROGRESS;
            case "已结束":
                return ActivityState.END;
                default:
                    return null;
        }
    }

    @Override
    public Object getResult(CallableStatement callableStatement, int columnIndex) throws SQLException {
        String state = callableStatement.getString(columnIndex);
        return getEnumByState(state);
    }
}