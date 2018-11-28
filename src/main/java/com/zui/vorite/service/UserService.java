package com.zui.vorite.service;

import com.zui.vorite.pojo.User;

import java.util.List;

/**
 *  User的api
 * @author Dusk
 */
public interface UserService {

    /**
     * byID
     * @param id
     * @return
     */
    User getUser(Long id);

    /**
     *  name条件
     * @param name
     * @return
     */
    User getUser(String name);

    /**
     *  取出所有
     * @return
     */
    List<User> selectAll();

    /**
     *  认证
     * @param username
     * @param password
     * @return
     */
    User userProve(String username, String password);

    /**
     *  追加or更新
     * @param user
     * @return
     */
    int insertOrUpdate(User user);

    /**
     * user Delete
     * @param id
     * @return
     */
    int userDel(Long id);
}
