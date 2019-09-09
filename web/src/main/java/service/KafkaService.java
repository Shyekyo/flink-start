package service;

/**
 * Created by zhangxiaofan on 2019/9/9.
 */
public interface KafkaService {
    void send(String key, String value);

    String makeLog(String userId, String productId, String action);
}
