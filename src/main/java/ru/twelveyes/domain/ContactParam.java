package ru.twelveyes.domain;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

/**
 * User: Krainov
 * Date: 27.02.14
 * Time: 18:08
 */
public class ContactParam {

    public final static String FIRST_NAME_PARAM = "";
    public final static String SECOND_NAME_PARAM = "";
    public final static String DATE_BIRTHDAY_PARAM = "";
    public final static String EMAIL_PARAM = "";
    public final static String PHONE_PARAM = "";
    public final static String LATITUDE_PARAM = "";
    public final static String LONGITUDE_PARAM = "";
    public final static String ADDRESS_PARAM = "";
    public final static String CITY_PARAM = "";
    public final static String UNDERGROUND_PARAM = "";

    private final String name;
    private final Object value;
    private final boolean isPrivate;
    private final Type paramType;

    public ContactParam(String name, Object value, boolean isPrivate, Type paramType) {
        this.name = name;
        this.value = value;
        this.isPrivate = isPrivate;
        this.paramType = paramType;
    }

    public enum Type {
        GEO,
        PROFILE,
        ADDRESS
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public Type getParamType() {
        return paramType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactParam that = (ContactParam) o;

        if (!name.equals(that.name)) return false;
        if (paramType != that.paramType) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + paramType.hashCode();
        return result;
    }
}
