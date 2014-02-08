package ru.twelveyes.config;

import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by lameroot on 28.12.13.
 */
@Configuration
@EnableAspectJAutoProxy
@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
public class MysqlEntityManagerConfig extends modules.config.db.mysql.MysqlEntityManagerConfig {

    @Override
    public String[] getPackagesToScan() {
        return new String[]{"ru.twelveyes.domain"};
    }
    @Override
    public boolean showSql() {
        return true;
    }

    @Override
    public String hbm2ddlAuto() {
        return HBM2DDLAuto.UPDATE.getValue();
    }
}
