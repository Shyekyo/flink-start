import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by zhangxiaofan on 2019/9/9.
 */
@SpringBootApplication
@MapperScan
public class RecommandApplication {
    public static void main(String[] args) {
        SpringApplication.run(RecommandApplication.class);
    }
}
