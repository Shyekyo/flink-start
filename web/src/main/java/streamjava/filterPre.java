package streamjava;

import java.util.function.Predicate;

/**
 * Created by zhangxiaofan on 2019/9/9.
 */
public class filterPre implements Predicate<String> {
    @Override
    public boolean test(String s) {
        if(s.equals("a")){
            return true;
        }
        return false;
    }
}
