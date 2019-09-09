package service;

import domain.ContactEntity;

import java.util.List;

/**
 * Created by zhangxiaofan on 2019/9/9.
 */
public interface ContactService {
    /**
     * 根据id列表筛选产品
     * @param ids
     * @return
     */
    List<ContactEntity> selectByIds(List<String> ids);

    /**
     * 根据id 筛选产品
     * @param id
     * @return
     */
    ContactEntity selectById(String id);
}
