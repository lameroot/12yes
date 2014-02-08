package ru.twelveyes.config;

import modules.config.db.mysql.MysqlSimpleDataSourceConfig;
import org.springframework.context.annotation.Configuration;

/**
 * Created by lameroot on 28.12.13.
 */
@Configuration
public class MysqlDataSourceConfig extends MysqlSimpleDataSourceConfig{

    @Override
    public String getSchemaName() {
        return "yes_base";
    }
}
