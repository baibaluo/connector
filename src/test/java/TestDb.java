import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit38.AbstractJUnit38SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Luo
 * Date: 11-9-30
 * Time: 上午9:23
 */

@ContextConfiguration(locations={"classpath:spring-db.xml"})
public class TestDb extends AbstractJUnit38SpringContextTests {

    @Autowired
    JdbcTemplate jdbcTemplate;


/*    public void test1(){
        List<Map<String, Object>> a = jdbcTemplate.queryForList("select * from dist_item_info");

        System.out.println(a);
    }*/


    public void testInsertRule(){
        jdbcTemplate.update("insert into rule (name, exp, remark, create_time) values (?, ?, ?, now())", "", "", "");

    }






/*    @Override
    protected String[] getConfigLocations() {
        String[] location = {"classpath:applicationContext-beans.xml", "classpath:applicationContext-datasource.xml"};
        return location;
    }*/
}
