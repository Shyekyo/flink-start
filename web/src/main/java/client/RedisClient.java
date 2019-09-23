package client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import streamjava.filterPre;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by zhangxiaofan on 2019/9/9.
 * ValueOperations：简单K-V操作
 * SetOperations：set类型数据操作
 * ZSetOperations：zset类型数据操作
 * HashOperations：针对map类型的数据操作
 * ListOperations：针对list类型的数据操作
 */
@Component
public class RedisClient {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 获取redis数据
     * @param key
     * @return
     */
    public String getData(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void setData(String key, String value) {
        redisTemplate.opsForValue().set(key,value);
    }

    /**
     * 获取 top 榜单
     * @param topRange
     * @return
     */
    public List<String> getTopList(int topRange){
        List<String> res = new ArrayList<>();

        for (int i = 0; i < topRange; i++) {
            res.add(getData(String.valueOf(i)));
        }
        return res;
    }

    /**
     * 获取1小时内接入量数据
     * @return
     */
    public String getMeter(){
        return getData("meter");
    }

    public static void main(String[] args) {
        Map<String,Integer> strs = new HashMap<String,Integer>() {{
            put("a",1);
            put("b",2);
            put("c",3);
        }};
        /* strs.forEach((k,v) -> {
            System.out.println(k + " : "+ v);
        });
        stream()
        .filter(b -> b.getColor() == RED)
         .sorted((x,y) -> x.getWeight() - y.getWeight())
         .mapToInt(Widget::getWeight)
         .sum();*/
        List<String> list = new ArrayList<String>(){{
            add("a");
            add("b");
            add("c");
        }};
        List<String> collect = list.stream().filter(new filterPre()).collect(Collectors.toList());
        collect.forEach(a -> System.out.println(a));
    }
}
