package ru.twelveyes.domain;

import org.junit.Test;

import java.util.Map;

/**
 * Created by lameroot on 27.02.14.
 */
public class ContactTest extends AbstractDomainTest {

    @Test
    public void testCreateContact() {
        Contact contact = new Contact();
        contact.addFirstName("stas");

        template.save(contact);

        Contact contact1 = template.findOne(contact.getId(),Contact.class);
//        System.out.println("contact1.getParams() = " + contact1.getParams());
//        for (Map.Entry<String,Object> o : contact1.getParams().asMap().entrySet()) {
//            System.out.println(o.getKey() + ":" + o.getValue());
//        }


    }
}
