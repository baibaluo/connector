package dianking.connector.controller;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Luo
 * Date: 11-11-12
 * Time: 上午11:13
 */

@Controller
public class RuleController {
    Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping("/rule/list")
    public String list(HttpServletRequest request) {

        //查询出所有规则
        List<Map<String, Object>> ruleList = jdbcTemplate.queryForList("select * from rule order by id");

        request.setAttribute("ruleList", ruleList);

        return "rule_list";
    }

    @RequestMapping("/rule/get")
    public void get(HttpServletRequest request, PrintWriter printWriter) {
        String id = request.getParameter("id");

        //查询规则
        Map<String, Object> rule = jdbcTemplate.queryForMap("select * from rule where id=?", id);

        printWriter.print(JSONObject.fromObject(rule).toString());
        printWriter.flush();
        printWriter.close();
    }

    @RequestMapping("/rule/add")
    public String add(HttpServletRequest request, String exp) {
        log.info(exp);

        JSONObject expObject = JSONObject.fromObject(exp);
        String name = expObject.getString("name");
        String remark = expObject.getString("remark");

        String actExp = expObject.getString("acts");
        String cdtExp = expObject.getString("cdts");

        //新增规则
        jdbcTemplate.update("insert into rule (name, remark, acts, cdts, create_time) values (?, ?, ?, ?, now())", name, remark, actExp, cdtExp);

        return this.list(request);
    }


    @RequestMapping("/rule/modify")
    public String modify(HttpServletRequest request, String id, String exp) {
        log.info(exp);

        JSONObject expObject = JSONObject.fromObject(exp);
        String name = expObject.getString("name");
        String remark = expObject.getString("remark");

        String actExp = expObject.getString("acts");
        String cdtExp = expObject.getString("cdts");

        //更新规则
        jdbcTemplate.update("update rule set name=?, remark=?, acts=?, cdts=? where id=?", name, remark, actExp, cdtExp, id);

        return this.list(request);
    }

    @RequestMapping("/rule/delete")
    public String delete(HttpServletRequest request, int id) {
        log.info(JSONObject.fromObject(request.getParameterMap()).toString());

        jdbcTemplate.update("delete from rule where id=?", id);

        return this.list(request);
    }


}
