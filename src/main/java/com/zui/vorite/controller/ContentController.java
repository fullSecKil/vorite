package com.zui.vorite.controller;

import com.zui.vorite.pojo.User;
import com.zui.vorite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @file: ContentController.class
 * @author: Dusk
 * @since: 2019/1/5 0:27
 * @desc:
 */

@RestController
@RequestMapping("/caricature")
public class ContentController {

    private UserService userService;

    @Autowired
    void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/manage/user_header")
    public Object resHeader(@RequestParam("id") Long id){
        User u = userService.getUser(id);
        Map<String, Object> map = new HashMap<>();
        map.put("user", u);
        return map;
    }
}
