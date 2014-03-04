package ru.twelveyes.domain;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.fieldaccess.DynamicProperties;
import org.springframework.data.neo4j.fieldaccess.DynamicPropertiesContainer;
import org.springframework.data.neo4j.support.index.IndexType;
import ru.twelveyes.util.JsonUtil;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by lameroot on 28.12.13.
 */
@NodeEntity
public class Contact {

    public final static String LOCATION_INDEX_NAME = "locations";

    @GraphId
    private Long id;
    @Indexed(indexType = IndexType.POINT, indexName = LOCATION_INDEX_NAME)
    private String wkt;
    private DynamicProperties params = new DynamicPropertiesContainer();

    public Contact addContactParam(String name, Object value, ContactParam.Type type) throws IOException {
        return addContactParam(name, value, type,false);
    }
    public Contact addContactParam(String name, Object value, ContactParam.Type type, boolean isPrivate) throws IOException {
        ContactParam contactParam = new ContactParam(name,value,type,isPrivate);
        params.setProperty(name, JsonUtil.toJson(contactParam));
        return this;
    }
    public Contact updateContactParam(String name, String newValue) throws IOException{
        ContactParam contactParam = getContactParam(name);
        if ( null == contactParam ) return this;
        contactParam.setValue(newValue);
        params.setProperty(name,JsonUtil.toJson(contactParam));
        return this;
    }
    public Contact removeContactParam(String name) {
        params.removeProperty(name);
        return this;
    }
    public Set<ContactParam> getContactParams() throws IOException {
        Set<ContactParam> contactParams = new HashSet<>();
        for (Map.Entry<String, Object> entry : params.asMap().entrySet()) {
            String value = (String)entry.getValue();
            ContactParam contactParam = JsonUtil.toObject(ContactParam.class,value);
            contactParams.add(contactParam);
        }
        return contactParams;
    }

    public Set<ContactParam> getContactParams(ContactParam.ContactConditional conditional) throws IOException {
        Set<ContactParam> contactParams = new HashSet<>();
        for (Map.Entry<String, Object> entry : params.asMap().entrySet()) {
            String value = (String)entry.getValue();
            ContactParam contactParam = JsonUtil.toObject(ContactParam.class,value);
            if ( conditional.check(contactParam) ) contactParams.add(contactParam);
        }
        return contactParams;
    }

    public ContactParam getContactParam(String name) throws IOException {
        String value = (String)params.getProperty(name);
        if ( null == value ) return null;
        return JsonUtil.toObject(ContactParam.class,value);
    }


    public void setLocation(double lon, double lat) {
        this.wkt = String.format("POINT( %s %s )",lon,lat).replace(",",".");
    }

    public String getLocation() {
        return wkt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DynamicProperties getParams() {
        return params;
    }

    public void setParams(DynamicProperties params) {
        this.params = params;
    }


}
