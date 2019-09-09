package service.impl;

import dao.ContactDao;
import domain.ContactEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.ContactService;

import java.util.List;

/**
 * Created by zhangxiaofan on 2019/9/9.
 */
@Service("contactService")
public class ContactServiceImpl implements ContactService {

    @Autowired
    ContactDao contactDao;

    @Override
    public List<ContactEntity> selectByIds(List<String> ids) {
        return contactDao.selectByIds(ids);
    }

    @Override
    public ContactEntity selectById(String id) {
        return contactDao.selectById(Integer.valueOf(id));
    }
}
