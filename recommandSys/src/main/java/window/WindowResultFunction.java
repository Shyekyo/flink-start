package window;

import domain.TopProductEntity;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

/**
 * Created by zhangxiaofan on 2019/9/5.
 */                                                        //in,         out,        key,      w
public class WindowResultFunction implements WindowFunction<Long, TopProductEntity, Tuple, TimeWindow> {
    @Override
    public void apply
            (Tuple key, TimeWindow window, Iterable<Long> aggregateResult , Collector<TopProductEntity> collector) {
        int itemId = key.getField(0);
        Long count = aggregateResult.iterator().next();
        collector.collect(TopProductEntity.of(itemId,window.getEnd(),count));
    }
}
