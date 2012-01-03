package dianking.connector.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Luo
 * Date: 11-12-10
 * Time: 下午12:26
 */
@Service
public class SupToDistProcessor {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public void process() {
        //查询sup
        List<Map<String, Object>> supList = jdbcTemplate.queryForList("select * from supply_dist where flag=0 or flag=1");

        //遍历sup
        for (Map<String, Object> supInfo : supList) {
            String supName = (String) supInfo.get("sup_name");
            String distName = (String) supInfo.get("dist_name");
            String sqlGetSupPrd = "select * from sup_item_info where t_nick=? and nick=? and newflag=1 and picflag=0";
            //判断淘宝商品，增加一个条件
            boolean isTaoBao = supInfo.get("flag").toString().equals("1");
            if (isTaoBao) {
                sqlGetSupPrd += "and shopflag=0";
            }
            //查询sup商品
            List<Map<String, Object>> supPrdList = jdbcTemplate.queryForList(sqlGetSupPrd, distName, supName);
            //遍历sup商品
            for (Map<String, Object> supPrd : supPrdList) {
                //淘宝商品判断图片上传
                if (isTaoBao) {
                    int count = jdbcTemplate.queryForInt("select count(*) as pnum from picrelate " +
                            "where t_nick=? and s_item_id=? and upflag!=0", distName, supPrd.get("num_iid"));
                    if (count > 0) {
                        //跳过此商品
                        continue;
                    }
                }
                //取得distCids
                String distCids = ",";
                String supCids = (String) supPrd.get("seller_cids");
                if (supCids != null && supCids.length() > 0) {
                    for (String supCid : supCids.split(",")) {
                        int distCid = jdbcTemplate.queryForInt("select t_cid from shopcat where t_nick=? and s_nick=? and s_cid=?"
                                , distName, supName, supCid);
                        distCids += distCids + ",";
                    }
                }

                //查询dist
                Map distPrd = jdbcTemplate.queryForMap("select *  from dist_item_info  where nick=? and s_nick=? and s_num_iid="
                        , distName, supName, supPrd.get("num_iid"));

                if (distPrd != null) {
                    String sqlUpdate = "update dist_item_info set" +
                            " sku=?, props_name=?, promoted_service=?, is_lightning_consignment=?, cid=?" +
                            ", seller_cids=?, props=?, input_pids=?, input_str=?, num=?" +
                            ", valid_thru=?, price=?, post_fee=?, express_fee=?, ems_fee=?" +
                            ", has_discount=?, freight_payer=?, has_invoice=?, has_warranty=?, approve_status=?" +
                            ", auction_point=?, property_alias=?, outer_id=?, is_virtual=?, video=?" +
                            ", after_sale_id=?, sell_promise=?, prd_desc=?, flag=1" +
                            " where " +
                            "nick=? and s_nick=? and s_num_iid=?";
                    jdbcTemplate.update(sqlUpdate
                            , supPrd.get("sku"), supPrd.get("props_name"), supPrd.get("promoted_service"), supPrd.get("is_lightning_consignment"), supPrd.get("cid")
                            , distCids, supPrd.get("props"), supPrd.get("input_pids"), supPrd.get("input_str"), supPrd.get("num")
                            , supPrd.get("valid_thru"), supPrd.get("price"), supPrd.get("post_fee"), supPrd.get("express_fee"), supPrd.get("ems_fee")
                            , supPrd.get("has_discount"), supPrd.get("freight_payer"), supPrd.get("has_invoice"), supPrd.get("has_warranty"), supPrd.get("approve_status")
                            , supPrd.get("auction_point"), supPrd.get("property_alias"), supPrd.get("outer_id"), supPrd.get("is_virtual"), supPrd.get("video")
                            , supPrd.get("after_sale_id"), supPrd.get("sell_promise"), supPrd.get("prd_desc")
                            , distName, supName, supPrd.get("num_iid"));
                } else {
                    String sqlInsert = "insert into dist_item_info " +
                            "( title, nick, detail_url, type" +
                            ", sku, props_name, created, promoted_service, is_lightning_consignment" +
                            ", is_fenxiao, cid, seller_cids, props, input_pids" +
                            ", input_str, pic_url, num, valid_thru, list_time" +
                            ", delist_time, stuff_status, location, price, post_fee" +
                            ", express_fee, ems_fee, has_discount, freight_payer, has_invoice" +
                            ", has_warranty, has_showcase, modified, increment, approve_status" +
                            ", product_id, auction_point, property_alias, outer_id, is_virtual" +
                            ", is_taobao, is_ex, is_timing, video, is_3D" +
                            ", score, volume, one_station, second_kill, auto_fill" +
                            ", violation, is_prepay, ww_status, wap_desc, wap_detail_url" +
                            ", after_sale_id, cod_postage_id, sell_promise, prd_desc, s_num_iid" +
                            ", s_nick ) values (";
                    jdbcTemplate.update(sqlInsert
                    , supPrd.get("title"), distName, supPrd.get("detail_url"), supPrd.get("type")
                    , supPrd.get("sku"), supPrd.get("props_name"), supPrd.get("created"), supPrd.get("promoted_service"), supPrd.get("is_lightning_consignment")
                    , supPrd.get("is_fenxiao"), supPrd.get("cid"), distCids, supPrd.get("props"), supPrd.get("input_pids")
                    , supPrd.get("input_str"), supPrd.get("pic_url"), supPrd.get("num"), supPrd.get("valid_thru"), supPrd.get("list_time")
                    , supPrd.get("delist_time"), supPrd.get("stuff_status"), supPrd.get("location"), supPrd.get("price"), supPrd.get("post_fee")
                    , supPrd.get("express_fee"), supPrd.get("ems_fee"), supPrd.get("has_discount"), supPrd.get("freight_payer"), supPrd.get("has_invoice")
                    , supPrd.get("has_warranty"), supPrd.get("has_showcase"), supPrd.get("modified"), supPrd.get("increment"), supPrd.get("approve_status")
                    , supPrd.get("product_id"), supPrd.get("auction_point"), supPrd.get("property_alias"), supPrd.get("outer_id"), supPrd.get("is_virtual")
                    , -1, -1, -1, supPrd.get("video"), -1
                    , -1, -1, -1, -1, -1
                    , -1, -1, -1, supPrd.get("wap_desc"), supPrd.get("wap_detail_url")
                    , -1, -1, -1, supPrd.get("prd_desc"), supPrd.get("num_iid")
                    , supName);
                }
            }

        }

    }

    public void updatePrd() {

    }
}
