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

@Controller("/itemSynRule")
public class ItemSynRuleController {
    Logger log = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/getAll")
    public String getAll() {
        //todo 查询出该商品对应的所有规则
        return "test";
    }

    @RequestMapping("/modifyAll")
    public String modifyAll() {

        //todo 删除旧规则，重新添加

        return null;
    }


}
