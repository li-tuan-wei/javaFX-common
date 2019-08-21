package ldh.common.mybatis;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ldh on 2016/12/25.
 */
public class ValuedEnumTypeHandler<E extends Enum<E>> extends BaseTypeHandler<ValuedEnum> {

    private Class<ValuedEnum> clazz;

    public ValuedEnumTypeHandler(Class<ValuedEnum> clazz) {
        if (clazz == null)
            throw new IllegalArgumentException("clazz argument cannot be null");
        this.clazz = clazz;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, ValuedEnum parameter, JdbcType jdbcType) throws SQLException {
        ps.setObject(i, parameter.getValue());
    }

    @Override
    public ValuedEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return transfer(rs.getObject(columnName));
    }

    @Override
    public ValuedEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return transfer(rs.getObject(columnIndex));
    }

    @Override
    public ValuedEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return transfer(cs.getObject(columnIndex));
    }

    private ValuedEnum transfer(Object value) {
        ValuedEnum[] objs = clazz.getEnumConstants();
        if (value == null) return null;
        for (ValuedEnum em : objs) {
            if (value instanceof Number) {
                Number n = (Number) value;
                Number n1 = (Number) em.getValue();
                if (n.longValue() == n1.longValue()) {
                    return em;
                }
            }
            if (em.getValue().equals(value)) {
                return em;
            }
        }
        throw new RuntimeException("从枚举中不能找到对应的值：" + value + ",  " + value.getClass() + ", " + clazz);
    }
}