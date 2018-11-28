package com.zui.vorite.controller;

import com.zui.vorite.pojo.Caricature;
import com.zui.vorite.pojo.User;
import com.zui.vorite.service.CaricatureService;
import com.zui.vorite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Dusk
 */
@Controller()
@RequestMapping("/caricature/manage")
public class ManageController {

    @Value("${spring.servlet.multipart.location}")
    private String UPLOAD_PATH;

    private BCryptPasswordEncoder passwordEncoder;

    private UserService userService;

    private CaricatureService caricatureService;


    @Autowired
    void getPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    void getUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    void getCaricatureService(CaricatureService caricatureService) {
        this.caricatureService = caricatureService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping(value = "/caricature_list")
    public String caricatureList() {
        return "caricature_list";
    }

    /**
     * 成员列表
     *
     * @param model
     * @return
     */
    @GetMapping(value = "/user_list")
    public String userList(Model model) {
        model.addAttribute(userService.selectAll());
        model.addAttribute(new User());
        return "user_list";
    }

    /**
     * 成员追加
     *
     * @param file
     * @param user
     * @return
     */
    @Validated
    @PostMapping(value = "/user_form")
    public String userCreate(@RequestPart("profilePicture") MultipartFile file, @Validated User user) {

        User userCaricature = user;
        Optional.ofNullable(file).map(MultipartFile::getOriginalFilename).ifPresent(n -> user.setHeader(UPLOAD_PATH + n));
        userCaricature.setPassword(passwordEncoder.encode(userCaricature.getPassword()));
        int a = userService.insertOrUpdate(user);
        return "redirect:/caricature/manage/user_list";
    }

    @GetMapping(value = "/user_edit")
    @ResponseBody
    public Map<String, Object> userEdit(@RequestParam("id") Long id) {
        User editUser = userService.getUser(id);
        Map<String, Object> result = new HashMap<>(2);
        result.put("user", editUser);
        return result;
    }

    @GetMapping(value = "/user_del/{id}")
    public String userDel(@PathVariable("id") Long id) {
        userService.userDel(id);
        return "redirect:/caricature/manage/user_list";
    }

    @GetMapping(value = "/action_list")
    public String activeList() {
        return "action_list";
    }


    /**
     * 漫画list
     * @param model
     * @return
     */
    @GetMapping(value = "/picture_list")
    public String pictureList(Model model) {
        List<Caricature> caricatureList = caricatureService.selectAll();
        model.addAttribute(caricatureList);
        return "caricature_list";
    }
}
