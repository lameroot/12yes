package ru.twelveyes.config;

import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.springframework.context.annotation.*;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.core.GraphDatabase;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by lameroot on 17.01.14.
 */
@Configuration
//@ImportResource(value = {"classpath:spring/neo4j-spring.xml"})
@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
public class Neo4jConfig extends Neo4jConfiguration{

    @Bean
    public EmbeddedGraphDatabase graphDatabaseService() {
        EmbeddedGraphDatabase graphDatabase = new EmbeddedGraphDatabase("data/graph.db");

        return graphDatabase;
    }

//    @Override
//    @Bean(name = "neo4jTransactionManager")
//    public PlatformTransactionManager neo4jTransactionManager() throws Exception {
//        return super.neo4jTransactionManager();
//    }
}
