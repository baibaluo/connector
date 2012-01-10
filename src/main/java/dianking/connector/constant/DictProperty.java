package dianking.connector.constant;

import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Luo
 * Date: 11-11-26
 * Time: 下午3:34
 */
public class DictProperty {

    public static String propertiesJson = "{" +
            "'title':['标题','text']," +
            "'id':['ID','number']," +
            "'num':['数量','number']," +
            "'price':['价格','number']}";

    public static Map<String, List<String>> propertiesMap = (Map<String, List<String>>) JSONObject.toBean(JSONObject.fromObject(propertiesJson), Map.class);


    public static void main(String[] args) {
        System.out.println(propertiesMap.get("num").get(1));

    }

}
