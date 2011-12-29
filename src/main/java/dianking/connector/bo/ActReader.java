package dianking.connector.bo;

import dianking.connector.constant.DictProperty;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Created by IntelliJ IDEA.
 * User: Luo
 * Date: 11-11-26
 * Time: 下午4:38
 */
public class ActReader {

    public static String read(String propertyName, String actType, String param) {
        String propertyType = DictProperty.propertiesMap.get(propertyName).get(1);

        String sql = "";
        if (propertyType.equals("text")) {
            switch (actType.charAt(0)) {
                //前插
                case '1': {
                    sql = String.format(" %s='%s'+%s ", propertyName, param, propertyName);
                    break;
                }
                //后缀
                case '2': {
                    sql = String.format(" %s=%s+'%s' ", propertyName, propertyName, param);
                    break;
                }
                //替换
                case '3': {
                    String[] params = param.split(",");
                    sql = String.format(" %s=replace(%s, '%s', '%s') ", propertyName, propertyName, params[0], params[1]);
                    break;
                }
            }
        } else if (propertyType.equals("number")) {
            sql = String.format(" %s=%s%s ", propertyName, propertyName, param);
        }
        return sql;
    }

    public static String read(String exp) {
        StringBuilder sqlSet = new StringBuilder();
        JSONArray acts = JSONArray.fromObject(exp);
        for (Object o : acts) {
            JSONArray actInfo = (JSONArray) o;
            String sqlPart = read((String) actInfo.get(0), (String) actInfo.get(1), (String) actInfo.get(2));
            if(sqlPart != null && sqlPart.length() > 0){
                sqlSet.append(sqlPart)
                        .append(",");
            }
        }
        //去掉最后一个","
        sqlSet.setLength(sqlSet.length() - 1);
        return sqlSet.toString();
    }

    public static void main(String[] args) {
        JSONObject.fromObject("{\"acts\":[],\"cdts\":[],\"name\":\"规则4\",\"remark\":\"规则4规则4\"}");

        String actExp = "[[\"title\",\"1\", \"新品 \"], [\"title\",\"3\", \"2011,2012\"]]";
        read(actExp);
    }
}
