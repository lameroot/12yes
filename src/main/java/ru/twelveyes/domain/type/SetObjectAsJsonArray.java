package ru.twelveyes.domain.type;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

import java.io.IOException;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by lameroot on 10.01.14.
 */
public class SetObjectAsJsonArray implements UserType {

    @Override
    public int[] sqlTypes() {
        return new int[]{Types.VARCHAR};
    }

    @Override
    public Class returnedClass() {
        return Set.class;
    }

    @Override
    public boolean equals(Object o, Object o2) throws HibernateException {
        return o.equals(o2);
    }

    @Override
    public int hashCode(Object o) throws HibernateException {
        return o.hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] strings, SessionImplementor sessionImplementor, Object o) throws HibernateException, SQLException {
        String json = resultSet.getString(strings[0]);
        if ( null == json ) return null;
        if ( "".equals(json) ) return new HashSet<String>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String[] values = objectMapper.readValue(json,String[].class);
            return new HashSet<String>(Arrays.asList(values));
        } catch (IOException e) {
            return new HashSet<String>();
        }
    }

    public static void main(String[] args) {
        String json = "[\"asd@asd.ru\",\"qwe@as.ru\",\"123@asd.ru\"]";

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String[] values = objectMapper.readValue(json,String[].class);
            HashSet<String> h = new HashSet<String>(Arrays.asList(values));
            System.out.println(h);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object o, int i, SessionImplementor sessionImplementor) throws HibernateException, SQLException {
        if ( null == o ) {
            preparedStatement.setNull(i,Types.VARCHAR);
            return;
        }
        if ( !(o instanceof String) ) throw new UnsupportedOperationException("can't convert " + o.getClass());

        //сделать конвертерт
    }

    @Override
    public Object deepCopy(Object o) throws HibernateException {
        return null;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Object o) throws HibernateException {
        return null;
    }

    @Override
    public Object assemble(Serializable serializable, Object o) throws HibernateException {
        return null;
    }

    @Override
    public Object replace(Object o, Object o2, Object o3) throws HibernateException {
        return null;
    }
}
