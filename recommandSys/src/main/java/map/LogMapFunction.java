package map;

import client.HbaseClient;
import domain.LogEntity;
import org.apache.flink.api.common.functions.MapFunction;
import util.LogToEntity;

/**
 * Created by zhangxiaofan on 2019/9/5.
 */
public class LogMapFunction implements MapFunction<String, LogEntity> {
    @Override
    public LogEntity map(String s) throws Exception {
        LogEntity log = LogToEntity.getLog(s);
        if (null != log){
            String rowKey = log.getUserId() + "_" + log.getProductId()+ "_"+ log.getTime();
            HbaseClient.putData("con",rowKey,"log","userid",String.valueOf(log.getUserId()));
            HbaseClient.putData("con",rowKey,"log","productid",String.valueOf(log.getProductId()));
            HbaseClient.putData("con",rowKey,"log","time",log.getTime().toString());
            HbaseClient.putData("con",rowKey,"log","action",log.getAction());
        }
        return log;
    }
}
