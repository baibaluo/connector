import junit.framework.TestCase;
import net.sf.json.JSONObject;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Luo
 * Date: 11-11-24
 * Time: 下午11:13
 */

public class Test extends TestCase {

    public void testJson() {
        Map map1 = new HashMap();
        map1.put("price", ">100");
        map1.put("store_quantity", "<10");
        map1.put("start_date", "<2011-01-01");

        JSONObject jsonObject = JSONObject.fromObject(map1);

        System.out.println(jsonObject);
    }


    public void testScript() throws ScriptException {
        // create a script engine manager
        ScriptEngineManager factory = new ScriptEngineManager();
        // create a JavaScript engine
        ScriptEngine engine = factory.getEngineByName("JavaScript");

        // JavaScript code in a String
        String script = "function hello(name) { print('Hello, ' + name['a']); }";
        // evaluate script
        engine.eval(script);
        engine.eval("hello({\"a\":1,\"b\":2})");
    }
}
