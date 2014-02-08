package ru.twelveyes.domain.neo4j;

import junit.framework.TestCase;
import modules.config.db.CacheConfig;
import org.aspectj.internal.lang.annotation.ajcDeclareSoft;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;
import ru.twelveyes.config.MysqlDataSourceConfig;
import ru.twelveyes.config.MysqlEntityManagerConfig;
import ru.twelveyes.config.Neo4jConfig;
import ru.twelveyes.domain.Activity;

import javax.annotation.Resource;
import javax.transaction.TransactionManager;

/**
 * Created by lameroot on 17.01.14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class,classes = {
        MysqlDataSourceConfig.class,
        MysqlEntityManagerConfig.class,
        Neo4jConfig.class
})
public class RelationshipTest extends TestCase{

    @Resource
    protected ApplicationContext applicationContext;
    @Resource
    protected Neo4jTemplate template;
    @Resource
    protected JtaTransactionManager transactionManager;
    @Resource
    protected PlatformTransactionManager jpaTransactionManager;

    @Test
    public void testExists() {
        assertNotNull(applicationContext);
        assertNotNull(template);
        assertNotNull(transactionManager);
        System.out.println(transactionManager);
        assertNotNull(jpaTransactionManager);
        System.out.println(jpaTransactionManager);
        transactionManager.
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
