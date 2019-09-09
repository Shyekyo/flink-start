package map;

import client.HbaseClient;
import client.MysqlClient;
import domain.LogEntity;
import org.apache.flink.api.common.functions.MapFunction;
import util.LogToEntity;

import java.sql.ResultSet;

/**
 * Created by zhangxiaofan on 2019/9/5.
 */
public class UserPortraitMapFunction implements MapFunction<String, String> {
    @Override
    public String map(String s) throws Exception {
        LogEntity log = LogToEntity.getLog(s);
        ResultSet rst = MysqlClient.selectById(log.getProductId());
        if (rst != null){
            while (rst.next()){
                String userId = String.valueOf(log.getUserId());

                String country = rst.getString("country");
                HbaseClient.increamColumn("user",userId,"country",country);
                String color = rst.getString("color");
                HbaseClient.increamColumn("user",userId,"color",color);
                String style = rst.getString("style");
                HbaseClient.increamColumn("user",userId,"style",style);
            }
        }
        return null;
    }
}
