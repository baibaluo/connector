package dianking.connector.bo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Luo
 * Date: 11-12-10
 * Time: 下午12:26
 */
@Service
public class RuleProcessor {
    Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    JdbcTemplate jdbcTemplate;

    public void process() {
        //查询通用规则
        List<Map<String, Object>> ruleList = jdbcTemplate.queryForList("select * from rule where valid=1 and scope=1");

        //遍历规则规则、更新商品信息
        for(Map<String, Object> rule : ruleList){
            log.info("规则 {} 开始处理 act_exp={} cdt_exp={}", new Object[]{rule.get("id"), rule.get("act_exp"), rule.get("cdt_exp")});
            String sqlSet = ActReader.read((String) rule.get("act_exp"));
            String sqlWhere = " where 1=1 " + CdtReader.read((String) rule.get("cdt_exp"));
            String sqlUpdate = "update dist_item_info set " + sqlSet + sqlWhere;
            log.info("规则 {} sql=[{}]", new Object[]{rule.get("id"), sqlUpdate});
            //更新
            int count = jdbcTemplate.update(sqlUpdate);
            log.info("规则 {} 处理完成 更新商品数量 {}", new Object[]{rule.get("id"), count});
        }
    }

}
