package map;

import client.HbaseClient;
import client.MysqlClient;
import domain.LogEntity;
import org.apache.flink.api.common.functions.MapFunction;
import util.AgeUtil;
import util.LogToEntity;

import java.sql.ResultSet;

/**
 * Created by zhangxiaofan on 2019/9/5.
 */
public class ProductPortraitMapFunction implements MapFunction<String,String> {
    @Override
    public String map(String s) throws Exception {
        LogEntity log = LogToEntity.getLog(s);
        ResultSet rst = MysqlClient.selectUserById(log.getUserId());
        if(rst!=null) {
            while (rst.next()) {
                String productId = String.valueOf(log.getProductId());
                String sex = rst.getString("sex");
                HbaseClient.increamColumn("prod", productId, "sex", sex);
                String age = rst.getString("age");
                HbaseClient.increamColumn("prod", productId, "age", AgeUtil.getAgeType(age));
            }
        }
        return null;
    }
}
