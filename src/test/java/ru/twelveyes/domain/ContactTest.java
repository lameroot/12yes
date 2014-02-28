package ru.twelveyes.domain;

import com.vividsolutions.jts.geom.Point;
import org.junit.Test;
import org.neo4j.gis.spatial.Layer;
import org.neo4j.gis.spatial.SpatialDatabaseService;
import org.springframework.data.neo4j.conversion.EndResult;
import ru.twelveyes.repository.ContactRepository;
import ru.twelveyes.util.GeoUtil;
import ru.twelveyes.util.JsonUtil;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by lameroot on 27.02.14.
 */
public class ContactTest extends AbstractDomainTest {

    @Resource
    private ContactRepository contactRepository;
    @Resource
    private SpatialDatabaseService spatialDatabaseService;

    @Test
    public void testCreateContact() throws Exception{
        Contact contact = new Contact();
        contact.addContactParam(ContactParam.FIRST_NAME_PARAM, "stas", ContactParam.Type.PROFILE);
        contact.setLocation(21.0,3.1);
        contactRepository.save(contact);


        Contact contact2 = new Contact();
        contact2.addContactParam(ContactParam.FIRST_NAME_PARAM, "stas2", ContactParam.Type.PROFILE).addContactParam(ContactParam.DATE_BIRTHDAY_PARAM,new Date(), ContactParam.Type.PROFILE,true);
        contact2.setLocation(30.0, 3.19);
        contactRepository.save(contact2);


        boolean layer = spatialDatabaseService.containsLayer("location");
        Layer layer1 = spatialDatabaseService.getLayer("location");
        System.out.println("laye1 = " + layer1.getIndex().count());
        System.out.println("layer = " + layer);


        EndResult<Contact> iterator = contactRepository.findWithinBoundingBox("location", 3.0,22.0,3.2,31.0);
        for (Contact contact1 : iterator) {
            System.out.println(contact1.getParams().asMap());
            System.out.println("sss: " + contact1.getLocation() + ":" + contact1.getContactParam(ContactParam.FIRST_NAME_PARAM));
            for (ContactParam param : contact1.getContactParams()) {
                System.out.println("params = " + param);
            }
            System.out.println(GeoUtil.parseWellKnowText(Point.class,contact1.getLocation()));

        }

        String contactParam = JsonUtil.toJson(new ContactParam("test",34,ContactParam.Type.PROFILE,true));
        System.out.println(contactParam);

//        Contact contact1 = template.findOne(contact.getId(),Contact.class);
//
//        System.out.println(contact1.getLocation());
//        System.out.println("contact1.getParams() = " + contact1.getParams());
//        for (Map.Entry<String,Object> o : contact1.getParams().asMap().entrySet()) {
//            System.out.println(o.getKey() + ":" + o.getValue());
//        }


    }
}
