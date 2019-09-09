package client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
}
