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

    public static String read(JSONObject actInfo) {
        String propertyName = (String) actInfo.get("propertyName");
        String actType = (String) actInfo.get("actType");
        String param1 = (String) actInfo.get("param1");
        String param2 = (String) actInfo.get("param2");
        String propertyType = DictProperty.propertiesMap.get(propertyName).get(1);

        String sql = "";
        if (propertyType.equals("text")) {
            switch (actType.charAt(0)) {
                //前插
                case '1': {
                    sql = String.format(" %s=concat('%s', %s) ", propertyName, param1, propertyName);
                    break;
                }
                //后缀
                case '2': {
                    sql = String.format(" %s=concat(%s, '%s') ", propertyName, propertyName, param1);
                    break;
                }
                //替换
                case '3': {
                    sql = String.format(" %s=replace(%s, '%s', '%s') ", propertyName, propertyName, param1, param2);
                    break;
                }
            }
        } else if (propertyType.equals("number")) {
            if(param1.matches("^\\d+$")){
                sql = String.format(" %s=%s ", propertyName, param1);

            } else {
                sql = String.format(" %s=%s%s ", propertyName, propertyName, param1);
            }
        }
        return sql;
    }

    public static String read(String exp) {
        StringBuilder sqlSet = new StringBuilder();
        JSONArray acts = JSONArray.fromObject(exp);
        for (Object o : acts) {
            JSONObject actInfo = (JSONObject) o;
            String sqlPart = read(actInfo);
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

        String actExp = "[{\"propertyName\":\"title\",\"actType\":\"3\",\"param1\":\"2011\",\"param2\":\"2012\"},{\"propertyName\":\"title\",\"actType\":\"1\",\"param1\":\"促销\"},{\"propertyName\":\"price\",\"param1\":\"*0.8\"}]";
        System.out.println(read(actExp));;
    }
}
