package ru.twelveyes.config;

import org.codehaus.jackson.map.ObjectMapper;
import org.neo4j.gis.spatial.SpatialDatabaseService;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.neo4j.server.WrappingNeoServerBootstrapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.rest.SpringRestGraphDatabase;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.twelveyes.util.GeoUtil;
import ru.twelveyes.util.JsonUtil;

/**
 * Created by lameroot on 17.01.14.
 */
@Configuration
@EnableTransactionManagement
@EnableNeo4jRepositories(basePackages = "ru.twelveyes.repository")
@ComponentScan(basePackages = "ru.twelveyes.service")
public class Neo4jConfig extends Neo4jConfiguration {

    private static final String DB_PATH = "data/graph.db";

    @Bean
    public GraphDatabaseService graphDatabaseService() {
        return new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
    }

    @Bean
    public SpatialDatabaseService spatialDatabaseService() {
        return new SpatialDatabaseService(graphDatabaseService());
    }


//    @Bean
//    public GraphDatabaseService graphDatabaseService() {
//        SpringRestGraphDatabase database = new SpringRestGraphDatabase("http://localhost:7474/db/data/");
//        return database;
//    }
}
