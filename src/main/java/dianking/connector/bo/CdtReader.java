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
public class CdtReader {

    public static String read(JSONObject cdtInfo){
        String propertyName = (String) cdtInfo.get("propertyName");
        String cdtType = (String) cdtInfo.get("cdtType");
        String param1 = (String) cdtInfo.get("param1");
        String param2 = (String) cdtInfo.get("param2");
        String andOr = (String) cdtInfo.get("andOr");
        String propertyType = DictProperty.propertiesMap.get(propertyName).get(1);

        String sql = "";
        if(propertyType.equals("text")){
            switch (cdtType.charAt(0)){
                //包含
                case '1':{
                    sql = String.format(" %s %s like '%%%s%%' ", andOr, propertyName, param1);
                    break;
                }
                //起始
                case '2':{
                    sql = String.format(" %s %s like '%s%%' ", andOr, propertyName, param1);
                    break;
                }
                //结束
                case '3':{
                    sql = String.format(" %s %s like '%%%s' ", andOr, propertyName, param1);
                    break;
                }
                //相等
                case '4':{
                    sql = String.format(" %s %s = '%s' ", andOr, propertyName, param1);
                    break;
                }
            }
        }
        else if(propertyType.equals("number")){
           sql = String.format(" %s %s%s ", andOr, propertyName, param1);
        }

       return sql;
    }

    public static String read(String exp){
        String whereStr = "";
        JSONArray acts = JSONArray.fromObject(exp);
        int index = 0;
        for(Object o : acts){
            JSONObject actInfo = (JSONObject) o;
            //第一个条件andOr属性强制为and
            if(index == 0){
                actInfo.element("andOr", "and");
            }
            whereStr += read(actInfo);
            index ++;
        }
        return whereStr;
    }

    public static void main(String[] args) {
        JSONArray.fromObject("[['a','b'], ['b']]");

        String actExp = "[['title','2', '新品', 'and'], ['price','', '<100', 'and']]";
        read(actExp);
    }
}
