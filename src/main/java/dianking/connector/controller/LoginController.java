package dianking.connector.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by IntelliJ IDEA.
 * User: Luo
 * Date: 12-2-8
 * Time: 下午2:29
 */
@Controller
public class LoginController {


    @RequestMapping
    public String login(HttpServletRequest request, PrintWriter printWriter, String userName, String password) throws IOException {

        if("luo".equals(userName) && "111111".equals(password)){
            request.getSession().setAttribute("userId", "1");

            printWriter.print("ok");
            printWriter.flush();
            printWriter.close();
        }
        return null;



    }
}
