package com.zui.vorite.dao;

import com.zui.vorite.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.common.BaseMapper;

/**
 * 集成模板，事务回滚
 *
 * @author Dusk
 */
@Mapper
@Transactional(rollbackFor = Exception.class)
public interface UserMapper extends BaseMapper<User> {

    /**
     * 用户验证
     *
     * @param username
     * @param password
     * @return
     */
    @Select("select * from user where username=#{username} and password=#{password}")
    User userProve(String username, String password);

    @Select("select * from user where username=#{username}")
    User getUser(String username);
}
