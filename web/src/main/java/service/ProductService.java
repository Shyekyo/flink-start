package service;

import domain.ProductEntity;

import java.util.List;

/**
 * Created by zhangxiaofan on 2019/9/9.
 */
public interface ProductService {
    /**
     * 根据产品id转换为产品类
     * @param id
     * @return
     */
    ProductEntity selectById(String id);

    /**
     * 根据id列表筛选产品
     * @param ids
     * @return
     */
    List<ProductEntity> selectByIds(List<String> ids);

    List<String> selectInitPro(int topSize);

}
