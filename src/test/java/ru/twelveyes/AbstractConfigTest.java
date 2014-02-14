package ru.twelveyes;

import junit.framework.TestCase;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by lameroot on 28.12.13.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AbstractConfigTest extends TestCase{

    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    protected Neo4jTemplate template;
}
