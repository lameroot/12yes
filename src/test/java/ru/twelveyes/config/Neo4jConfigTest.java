package ru.twelveyes.config;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import ru.twelveyes.AbstractConfigTest;

import javax.annotation.Resource;

/**
 * User: Krainov
 * Date: 09.02.14
 * Time: 13:44
 */
@ContextConfiguration(loader = AnnotationConfigContextLoader.class,classes = {
        Neo4jConfig.class
})
public class Neo4jConfigTest extends AbstractConfigTest {

    @Resource
    protected ApplicationContext applicationContext;

    @Test
    public void testExist() {
        assertNotNull(applicationContext);
    }
}
