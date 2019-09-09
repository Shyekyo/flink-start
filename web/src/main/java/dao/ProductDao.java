package dao;

import domain.ProductEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by zhangxiaofan on 2019/9/9.
 */
@Mapper
public interface ProductDao {
    ProductEntity selectById(@Param("id") int id);

    List<ProductEntity> selectByIds(@Param("ids") List<String> ids);

    List<String> selectInitPro(@Param("size") int size);
}
