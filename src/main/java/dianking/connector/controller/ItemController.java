package dianking.connector.controller;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Luo
 * Date: 11-11-12
 * Time: 上午11:13
 */

@Controller
public class ItemController {
    Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping("/item/list")
    public String list(HttpServletRequest request) {
        List<Map<String,Object>> itemList = jdbcTemplate.queryForList("select id, title, price, num, list_time from sup_item_info order by need_syn desc, id asc");
        //查询出所有规则
        List<Map<String, Object>> ruleList = jdbcTemplate.queryForList("select * from rule order by id");
        request.setAttribute("itemList", itemList);
        request.setAttribute("ruleList", ruleList);
        return "item_list";
    }

    @RequestMapping("/item/modifySyn")
    public String modifySyn(HttpServletRequest request, String id) {

        //修改商品同步状态
        jdbcTemplate.update("update sup_item_info set need_syn=case need_syn when 0 then 1 else 0 end where id=?", id);

        return this.list(request);
    }

    @RequestMapping("/item/getItemRule")
    public void getItemRule(String itemId, PrintWriter printWriter) {

        String ruleId = null;
        try {
            ruleId = jdbcTemplate.queryForObject("select rule_id from item_rule where item_id=?", String.class, itemId);
        } catch (IncorrectResultSizeDataAccessException e) {

        }
        printWriter.print(ruleId);
        printWriter.flush();
    }

    @RequestMapping("/item/setItemRule")
    public String setItemRule(HttpServletRequest request, String itemId, String ruleId) {

        jdbcTemplate.update("delete from item_rule where item_id=?", itemId);
        if(ruleId != null &&ruleId.length() > 0){
            jdbcTemplate.update("insert into item_rule (item_id, rule_id) values(?, ?)", itemId, ruleId);

        }

        return this.list(request);
    }


}
