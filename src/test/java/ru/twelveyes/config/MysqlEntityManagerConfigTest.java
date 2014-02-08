package ru.twelveyes.config;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import ru.twelveyes.AbstractConfigTest;
import ru.twelveyes.domain.Profile;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;

/**
 * Created by lameroot on 11.01.14.
 */
@ContextConfiguration(classes = {
        MysqlDataSourceConfig.class,
        MysqlEntityManagerConfig.class
})
@Transactional
public class MysqlEntityManagerConfigTest extends AbstractConfigTest {

    @PersistenceContext
    protected EntityManager entityManager;
    @Resource
    protected PlatformTransactionManager transactionManager;

    @Test
    public void testExists() {
        assertNotNull(entityManager);
        assertNotNull(transactionManager);
        System.out.println(transactionManager);
    }


}
