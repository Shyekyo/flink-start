package task;

import map.LogMapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import util.Property;

import java.util.Properties;

/**
 * Created by zhangxiaofan on 2019/9/5.
 */
public class LogTask {
    public static void main(String[] args) throws Exception{
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        Properties pro = Property.getKafkaProperties("log");
        FlinkKafkaConsumer<String> con = new FlinkKafkaConsumer<String>("con", new SimpleStringSchema(), pro);
        DataStreamSource<String> dataStream = env.addSource(con);
        dataStream.map(new LogMapFunction());
        env.execute("Log message receive");
    }
}
