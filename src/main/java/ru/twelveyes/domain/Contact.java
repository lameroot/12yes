package ru.twelveyes.domain;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedToVia;
import org.springframework.data.neo4j.fieldaccess.DynamicProperties;
import org.springframework.data.neo4j.fieldaccess.DynamicPropertiesContainer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by lameroot on 28.12.13.
 */
@NodeEntity
public class Contact {

    @GraphId
    private Integer id;


    private DynamicProperties dynamicProperties = new DynamicPropertiesContainer();

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

    private Set<ContactParam> params = new HashSet<>();

    protected Contact addParam(ContactParam param) {
        params.add(param);
        return this;
    }
    public Contact addFirstName(String value) {
        dynamicProperties.setProperty(Contact.FIRST_NAME_PARAM,new ContactParam(ContactParam.FIRST_NAME_PARAM, value, false, ContactParam.Type.PROFILE));
        return this;
    }


    public Contact(String name, Object value, boolean isPrivate, Type paramType) {
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

        Contact that = (Contact) o;

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



    /*
    private String firstName;
    private String secondName;
    private MediaContent avatar;
    private List<MediaContent> contents;

    private String emails;
    private String phones;
    private String address;
    private double latitude;
    private double longitude;
     */

}
