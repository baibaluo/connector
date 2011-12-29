package dianking.connector.bo;

import dianking.connector.constant.DictProperty;
import net.sf.json.JSONArray;

/**
 * Created by IntelliJ IDEA.
 * User: Luo
 * Date: 11-11-26
 * Time: 下午4:38
 */
public class CdtReader {

    public static String read(String propertyName, String cdtType, String param, String andOr){
        String propertyType = DictProperty.propertiesMap.get(propertyName).get(1);

        String sql = "";
        if(propertyType.equals("text")){
            switch (cdtType.charAt(0)){
                //包含
                case '1':{
                    sql = String.format(" %s %s like '%%%s%%' ", andOr, propertyName, param);
                    break;
                }
                //起始
                case '2':{
                    sql = String.format(" %s %s like '%s%%' ", andOr, propertyName, param);
                    break;
                }
                //结束
                case '3':{
                    sql = String.format(" %s %s like '%%%s' ", andOr, propertyName, param);
                    break;
                }
                //相等
                case '4':{
                    sql = String.format(" %s %s = '%s' ", andOr, propertyName, param);
                    break;
                }
            }
        }
        else if(propertyType.equals("number")){
           sql = String.format(" %s %s%s ", andOr, propertyName, param);
        }

       return sql;
    }

    public static String read(String exp){
        String whereStr = "";
        JSONArray acts = JSONArray.fromObject(exp);
        for(Object o : acts){
            JSONArray actInfo = (JSONArray) o;
            whereStr += read((String) actInfo.get(0), (String) actInfo.get(1), (String) actInfo.get(2), (String) actInfo.get(3));
        }
        return whereStr;
    }

    public static void main(String[] args) {
        JSONArray.fromObject("[['a','b'], ['b']]");

        String actExp = "[['title','2', '新品', 'and'], ['price','', '<100', 'and']]";
        read(actExp);
    }
}
