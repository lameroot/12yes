package ru.twelveyes.config;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import ru.twelveyes.AbstractConfigTest;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * Created by lameroot on 28.12.13.
 */

@ContextConfiguration(
        classes = {
                MysqlDataSourceConfig.class
        }
)
public class MysqlDataSourceConfigTest extends AbstractConfigTest {

    @Resource
    private DataSource dataSource;

    @Test
    public void testExistsDataSource() {
        assertNotNull(dataSource);
    }

}
