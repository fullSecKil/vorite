package com.zui.vorite.controller;

import com.zui.vorite.pojo.Caricature;
import com.zui.vorite.pojo.GenreCaricature;
import com.zui.vorite.pojo.OperationLog;
import com.zui.vorite.pojo.User;
import com.zui.vorite.service.CaricatureService;
import com.zui.vorite.service.GenreCaricatureService;
import com.zui.vorite.service.OperationLogService;
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
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


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

    private OperationLogService operationLogService;

    @Autowired
    void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    void setCaricatureService(CaricatureService caricatureService) {
        this.caricatureService = caricatureService;
    }

    @Autowired
    void setOperationLogService(OperationLogService operationLogService) {
        this.operationLogService = operationLogService;
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
        // 存入cache
        userService.cachePutPassward(userCaricature.getEmail(), userCaricature.getPassword());

        Optional.ofNullable(file).filter(f-> !f.isEmpty()).map(MultipartFile::getOriginalFilename).ifPresent(n -> userCaricature.setHeader(UPLOAD_PATH + n));
        userCaricature.setPassword(passwordEncoder.encode(userCaricature.getPassword()));
        int a = userService.insertOrUpdate(userCaricature);
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

    /**
     * 漫画list
     * @param model
     * @return
     */
    @GetMapping(value = "/picture_list")
    public String pictureList(Model model) {
        List<Caricature> caricatureList = caricatureService.selectAll();
        model.addAttribute(caricatureList);
        model.addAttribute(new Caricature());
        return "caricature_list";
    }

    /**
     * 漫画添加
     * @param caricature
     * @return
     */
    @Validated
    @PostMapping(value = "/picture_form")
    public String pictureCreate(@Validated Caricature caricature) {

        Caricature picture = caricature;
        int a = caricatureService.insertOrUpdate(picture);
        return "redirect:/caricature/manage/picture_list";
    }

    /**
     * picture编辑
     * @param id
     * @return
     */
    @GetMapping(value = "/picture_edit")
    @ResponseBody
    public Map<String, Object> pictureEdit(@RequestParam("id") Long id) {
        Caricature caricature = caricatureService.getCaricature(id);
        Map<String, Object> result = new HashMap<>(2);
        result.put("caricature", caricature);
        return result;
    }

    /**
     * picture 删除拦截
     * @param id
     * @return
     */
    @GetMapping(value = "/picture_del/{id}")
    public String pictureDel(@PathVariable("id") Long id){
        caricatureService.caricatureDel(id);
        return "redirect:/caricature/manage/picture_list";
    }

    /**
     * 打印出操作
     * @param model
     * @return
     */
    @GetMapping(value = "/action_list")
    public String actionList(Model model){
        List<OperationLog> operationLog = operationLogService.seleteAll();
        model.addAttribute(operationLog);
        return "action_list";
    }

    @GetMapping(value = "/caricature_form")
    public String caricatureForm(Model model, @Value("#{genreCaricatureServiceImpl.selectAll()}") List<GenreCaricature> genreCaricatureServiceList){
        List<Caricature> caricatureList = caricatureService.selectAll();
        Map<String, Long> caricatureNameMap = caricatureList.stream().collect(Collectors.toMap(Caricature::getName, Caricature::getId, (key1, key2) -> key2));
        Map<Long, String> genreCaricatureServiceNameMap= genreCaricatureServiceList.stream().collect(Collectors.toMap(GenreCaricature::getId, GenreCaricature::getGenre));

        model.addAttribute("caricatureNameMap", caricatureNameMap);
        model.addAttribute("genreCaricatureServiceNameMap", genreCaricatureServiceNameMap);
        model.addAttribute(new Caricature());
        return "caracture_form";
    }

    @PostMapping(value = "/caricature_form")
    public String caricaturePut(@RequestPart("fileCaracture") MultipartFile file, @Validated Caricature caricature){
        // 稍后处理
        Caricature caricature1 = caricature;
        String regEx = "^(?<id>\\d+)-(?<name>.*)$";
        Pattern pattern = Pattern.compile(regEx);

        Optional.ofNullable(caricature1.getName()).map(n->pattern.matcher(n)).filter(Matcher::matches).ifPresent(m->{
            caricature1.setId(Long.parseUnsignedLong(m.group("id")));
            caricature1.setName(m.group("name"));
        });

        System.out.println("a");
        return "abc";
    }
}
