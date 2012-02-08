package dianking.connector.aop;

import dianking.connector.controller.LoginController;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * User: Luo
 * Date: 12-2-8
 * Time: 下午1:42
 */
@Service
public class SessionHandler extends HandlerInterceptorAdapter {
    Log log = LogFactory.getLog(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info(request.getRequestURI() + " parm=>" + JSONObject.fromObject(request.getParameterMap()).toString());

        //不拦截loginController
        if(handler instanceof LoginController){
            return true;
        }

        //拦截未登录请求
        if(request.getSession().getAttribute("userId") == null){
            log.info("there is no login session");
            response.sendRedirect("/static/page/login.html");
            return false;
        }
        return true;
    }
}
