package ru.twelveyes.service;

import org.springframework.data.neo4j.support.Neo4jTemplate;

import javax.annotation.Resource;

/**
 * User: Krainov
 * Date: 27.02.14
 * Time: 18:38
 */
public abstract class AbstractService<T extends Object> {

    @Resource
    protected Neo4jTemplate template;

}
