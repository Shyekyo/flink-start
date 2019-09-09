package task;

import map.GetLogFunction;
import map.UserHistoryWithInterestMapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import util.Property;

import java.util.Properties;

/**
 * Created by zhangxiaofan on 2019/9/5.
 */
public class UserInterestTask {

    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        Properties properties = Property.getKafkaProperties("interest");
        DataStreamSource<String> dataStream = env.addSource(new FlinkKafkaConsumer<String>("con", new SimpleStringSchema(), properties));
        dataStream.map(new GetLogFunction())
                .keyBy("userId")
                .map(new UserHistoryWithInterestMapFunction());

        env.execute("User Product History");
    }
}