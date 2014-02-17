package ru.twelveyes.config;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.neo4j.server.WrappingNeoServerBootstrapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.rest.SpringRestGraphDatabase;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by lameroot on 17.01.14.
 */
@Configuration
@EnableTransactionManagement
@EnableNeo4jRepositories(basePackages = "ru.twelveyes.repository")
public class Neo4jConfig extends Neo4jConfiguration {

    private static final String DB_PATH = "data/graph.db";

    /*
    @Bean
    public EmbeddedGraphDatabase graphDatabaseService() {
        EmbeddedGraphDatabase graphDatabase = new EmbeddedGraphDatabase(DB_PATH);
        return graphDatabase;
    }
    */
    @Bean
    public GraphDatabaseService graphDatabaseService() {
        SpringRestGraphDatabase database = new SpringRestGraphDatabase("http://localhost:7474/db/data/");
        return database;
    }

//    @Bean
//    public WrappingNeoServerBootstrapper neo4jWebServer() {
//        WrappingNeoServerBootstrapper server = new WrappingNeoServerBootstrapper(graphDatabaseService());
//        server.start();
//        return server;
//    }
}
