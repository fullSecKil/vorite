package com.zui.vorite.service.impl;

import com.zui.vorite.dao.UserMapper;
import com.zui.vorite.pojo.User;
import com.zui.vorite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * User业务
 *
 * @author Dusk
 */
@Service
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;

    @Autowired
    public void getMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }


    @Override
    public User getUser(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public User getUser(String name) {
        return userMapper.getUser(name);
    }

    @Override
    public List<User> selectAll() {
        return userMapper.selectAll();
    }

    @Override
    public User userProve(String username, String password) {
        return userMapper.userProve(username, password);
    }

    /**
     * Optional  避免空指针异常
     *
     * @param user
     * @return
     */
    @Override
    public int insertOrUpdate(User user) {
        int flag = Optional.ofNullable(user.getId()).filter(id -> userMapper.existsWithPrimaryKey(id)).map(u -> userMapper.updateByPrimaryKeySelective(user)).orElse(0);
        return flag == 0 ? userMapper.insertSelective(user) : flag;
        /**
         * 指令式vs声明式
         */
        /*        Optional<Long> optUserId = Optional.ofNullable(user.getId());
        optUserId.filter(id -> userMapper.existsWithPrimaryKey(id)).ifPresent(x -> userMapper.updateByPrimaryKey(user));
        if (optUserId.isPresent() && userMapper.existsWithPrimaryKey(user.getId())) {
            return userMapper.updateByPrimaryKey(user);
        } else {
            return userMapper.insertSelective(user);
        }*/
    }

    /**
     * @param id
     * @return
     */
    @Override
    public int userDel(Long id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    private static final Map<String, String> DATABASES = new HashMap<>();

    /**
     *
     * @param email
     * @return
     */
    @Cacheable(value = "password", key = "email")
    @Override
    public String cacheGetPassward(String email) {
        return DATABASES.get(email);
    }

    /**
     *  取出cache中密码
     * @param email
     * @param password
     */
    @Cacheable(value = "password", key = "email")
    @Override
    public void cachePutPassward(String email, String password) {
        DATABASES.put(email, password);
    }

    /**
     *  删除cache
     * @param email
     */
    @Cacheable(value = "password", key = "email")
    @Override
    public void cacheDeletePassward(String email) {
        DATABASES.remove(email);
    }

}
