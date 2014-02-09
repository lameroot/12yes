package ru.twelveyes.domain.neo4j;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import ru.twelveyes.config.Neo4jConfig;
import ru.twelveyes.domain.Activity;

import javax.annotation.Resource;

/**
 * Created by lameroot on 17.01.14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class,classes = {
        Neo4jConfig.class
})
public class RelationshipTest extends TestCase{

    @Resource
    protected ApplicationContext applicationContext;
    @Resource
    protected Neo4jTemplate template;


    @Test
    public void testExists() {
        assertNotNull(applicationContext);
        assertNotNull(template);
    }

    @Test
    public void testCreateActivity() {
        Activity activity1 = new Activity();
        activity1.setTitle("activity1");
        System.out.println(template);
        template.save(activity1);
        assertNotNull(activity1.getId());

        Activity activity2 = new Activity();
        activity2.setTitle("activoty2");
        activity2.addParent(activity1);
        template.save(activity2);
        assertNotNull(activity2.getId());
    }
}
