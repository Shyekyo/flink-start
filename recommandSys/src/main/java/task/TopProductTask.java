package task;

import Agg.CountAgg;
import domain.LogEntity;
import domain.TopProductEntity;
import map.TopProductMapFunction;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.timestamps.AscendingTimestampExtractor;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.connectors.redis.RedisSink;
import org.apache.flink.streaming.connectors.redis.common.config.FlinkJedisPoolConfig;
import org.apache.flink.util.Collector;
import sink.TopNRedisSink;
import top.TopNHotItems;
import util.Property;
import window.WindowResultFunction;

import java.util.List;
import java.util.Properties;

/**
 * Created by zhangxiaofan on 2019/9/5.
 */
public class TopProductTask {
    private static final int topSize = 5;

    public static void main(String[] args) throws Exception{
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        FlinkJedisPoolConfig conf = new FlinkJedisPoolConfig.Builder().
                setHost(Property.getStrValue("redis.host")).
//				.setPort(Property.getIntValue("redis.port"))
//				.setDatabase(Property.getIntValue("redis.db"))
                build();
        Properties properties = Property.getKafkaProperties("topProduct");
        DataStreamSource<String> dataStream = env.addSource(new FlinkKafkaConsumer<String>("con", new SimpleStringSchema(), properties));
        DataStream<TopProductEntity> topProduct = dataStream.map(new TopProductMapFunction())
                .assignTimestampsAndWatermarks(new AscendingTimestampExtractor<LogEntity>() {
                    @Override
                    public long extractAscendingTimestamp(LogEntity logEntity) {
                        long l = logEntity.getTime() * 1000;
                        return l;
                    }
                })
                .keyBy("productId").timeWindow(Time.seconds(60),Time.seconds(5))
                .aggregate(new CountAgg(),new WindowResultFunction())
                .keyBy("windowEnd")
                .process(new TopNHotItems(topSize)).flatMap(new FlatMapFunction<List<String>, TopProductEntity>() {
                    @Override
                    public void flatMap(List<String> strings, Collector<TopProductEntity> collector) throws Exception {
                        System.out.println("-------------Top N Product------------");
                        for (int i = 0; i < strings.size(); i++) {
                            TopProductEntity top = new TopProductEntity();
                            top.setRankName(String.valueOf(i));
                            top.setProductId(Integer.parseInt(strings.get(i)));
                            // 输出排名结果
                            System.out.println(top);
                            collector.collect(top);
                        }
                    }
                });
        topProduct.addSink(new RedisSink<>(conf,new TopNRedisSink()));

        env.execute("Top N ");

    }
}
