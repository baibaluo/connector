package dianking.connector.controller;

import dianking.connector.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Luo
 * Date: 12-2-8
 * Time: 下午2:29
 */
@Controller
public class LoginController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping
    public String login(HttpServletRequest request, PrintWriter printWriter, String userName, String password) throws IOException {

        String encodedPassword = MD5Util.getMD5(password);
        Map<String, Object> userInfo = null;
        try {
            userInfo = jdbcTemplate.queryForMap("select * from tuser where t_username=? and t_password=?", userName, encodedPassword);
        } catch (IncorrectResultSizeDataAccessException e) {
            //查询不到结果，忽略该异常
        }

        if (userInfo != null) {
            request.getSession().setAttribute("userId", userInfo.get("t_id"));
            printWriter.print("ok");
        } else {
            printWriter.print("fail");
        }
        printWriter.flush();
        printWriter.close();
        return null;


    }
}
