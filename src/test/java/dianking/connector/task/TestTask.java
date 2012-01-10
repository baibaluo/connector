package dianking.connector.task;

import dianking.connector.bo.RuleProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit38.AbstractJUnit38SpringContextTests;

/**
 * Created by IntelliJ IDEA.
 * User: Luo
 * Date: 12-1-3
 * Time: 上午10:56
 */

@ContextConfiguration(locations={"classpath:spring.xml"})
public class TestTask extends AbstractJUnit38SpringContextTests {

    @Autowired
    SupToDistTask supToDistTask;


    public void test1(){
        supToDistTask.supToDist();
    }

}
