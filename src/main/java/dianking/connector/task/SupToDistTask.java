package dianking.connector.task;

import dianking.connector.bo.RuleProcessor;
import dianking.connector.bo.SupToDistProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * User: Luo
 * Date: 11-12-12
 * Time: 下午3:07
 */
@Service
public class SupToDistTask {
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RuleProcessor ruleProcessor;
    @Autowired
    SupToDistProcessor supToDistProcessor;


    public void supToDist(){
        log.info("定时开始执行: supToDist");
        supToDistProcessor.process();
        ruleProcessor.process();
        log.info("定时执行完成: supToDist");
    }
}
