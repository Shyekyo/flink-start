package dao;

import domain.ContactEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by zhangxiaofan on 2019/9/9.
 */
@Mapper
public interface ContactDao {
    ContactEntity selectById(@Param("id") int id);

    List<ContactEntity> selectByIds(@Param("ids") List<String> ids);
}
