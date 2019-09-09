package Agg;

import domain.LogEntity;
import org.apache.flink.api.common.functions.AggregateFunction;

/**
 * Created by zhangxiaofan on 2019/9/5.
 */
public class CountAgg implements AggregateFunction<LogEntity,Long,Long> {
    @Override
    public Long createAccumulator() {
        return 0L;
    }

    @Override
    public Long add(LogEntity value, Long acc) {
        return acc+1;
    }

    @Override
    public Long getResult(Long acc) {
        return acc;
    }

    @Override
    public Long merge(Long acc, Long acc1) {
        return acc+acc1;
    }
}
