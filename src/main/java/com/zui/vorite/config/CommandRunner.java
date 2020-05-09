package com.zui.vorite.config;

import com.zui.vorite.pojo.User;
import com.zui.vorite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @file: CommandRunner.class
 * @author: Dusk
 * @since: 2019/3/16 17:24
 * @desc:
 */

/**
 *  spring boot 容器加载后自动监听
 */

@Component
public class CommandRunner implements CommandLineRunner {

    private UserService userService;

    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if(! Optional.ofNullable(userService.getUser("admin")).isPresent()){
            User user = new User("admin", passwordEncoder.encode("123456"), "en2213@163.com", null, null, null, "MEMBER,LEADER");
            userService.insertOrUpdate(user);
        }
    }
}
