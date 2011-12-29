package dianking.connector.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by IntelliJ IDEA.
 * User: Luo
 * Date: 11-11-12
 * Time: 上午11:13
 */

@Controller
public class TestController {
    Logger log = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/m.do")
    public String method1() {
        log.debug("method1 running ...");
        return "test";
    }
}
