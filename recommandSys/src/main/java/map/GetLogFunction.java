package map;

import domain.LogEntity;
import org.apache.flink.api.common.functions.MapFunction;
import util.LogToEntity;

/**
 * Created by zhangxiaofan on 2019/9/5.
 */
public class GetLogFunction implements MapFunction<String, LogEntity> {
    @Override
    public LogEntity map(String s) throws Exception {

        LogEntity log = LogToEntity.getLog(s);
        return log;
    }
}
