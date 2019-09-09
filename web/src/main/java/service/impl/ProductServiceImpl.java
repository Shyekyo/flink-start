package service.impl;

import dao.ProductDao;
import domain.ProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.ProductService;

import java.util.List;

/**
 * Created by zhangxiaofan on 2019/9/9.
 */
@Service("productService")
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductDao productDao;


    @Override
    public ProductEntity selectById(String id) {
        return productDao.selectById(Integer.valueOf(id));
    }

    @Override
    public List<ProductEntity> selectByIds(List<String> ids) {
        return productDao.selectByIds(ids);
    }

    @Override
    public List<String> selectInitPro(int topSize) {
        return productDao.selectInitPro(topSize);
    }

}
