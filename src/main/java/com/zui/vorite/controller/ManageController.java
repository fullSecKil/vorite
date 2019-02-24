package com.zui.vorite.controller;

import com.zui.vorite.pojo.Caricature;
import com.zui.vorite.pojo.GenreCaricature;
import com.zui.vorite.pojo.OperationLog;
import com.zui.vorite.pojo.User;
import com.zui.vorite.service.CaricatureService;
import com.zui.vorite.service.GenreCaricatureService;
import com.zui.vorite.service.OperationLogService;
import com.zui.vorite.service.UserService;
import com.zui.vorite.tools.ExtractZip;
import com.zui.vorite.tools.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.util.StringValueResolver;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
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

    @Value("${global.images.header-url}")
    private String HEADER_PATH;

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

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping
    public String index(Model model) {
        List<Caricature> caricatureList = caricatureService.selectAll();
        /*
        * caricatureList.stream().filter(c-> !StringUtils.isEmpty(c.getPath())).map(c-> {
            try {
                return Optional.of(new File(ResourceUtils.getURL("classpath:").getPath(), "static" + c.getPath()));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return Optional.empty();
        }).filter(p->p.isPresent()).map(p->p.get().toString()).map(p -> p + File.separator + new File(p).getName() + ".jpg").collect(Collectors.toList());*/
        List<String> cList= caricatureList.stream().filter(c->!StringUtils.isEmpty(c.getPath())).map(c-> MessageFormat.format("{0}/{1}.jpg", c.getPath(), c.getName())).collect(Collectors.toList());
        System.out.println(cList);
        model.addAttribute("caricatureList", cList);
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
    public String userCreate(@RequestPart("profilePicture") MultipartFile file, @Validated User user) throws IOException {

        User userCaricature = user;
        // 存入cache
        userService.cachePutPassward(userCaricature.getEmail(), userCaricature.getPassword());

        Optional.ofNullable(file).filter(f -> !f.isEmpty()).map(MultipartFile::getOriginalFilename).ifPresent(n -> userCaricature.setHeader(HEADER_PATH + File.separator + n));

        // 取出项目根目录

        File path = new File(ResourceUtils.getURL("classpath:").getPath());

        File upload = new File(path.getAbsolutePath(), MessageFormat.format("{0}{1}", "static", HEADER_PATH));

        if (!upload.exists()) {
            upload.mkdirs();
        }
        // 写入userheader目录
        file.transferTo(new File(upload + File.separator  + file.getOriginalFilename()));

        userCaricature.setPassword(passwordEncoder.encode(userCaricature.getPassword()));
        userCaricature.setPassword(userCaricature.getPassword());
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
     *
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
     *
     * @param caricature
     * @return
     */
    @Validated
    @PostMapping(value = "/picture_form")
    public String pictureCreate(@RequestPart("cover") MultipartFile file, @Validated Caricature caricature) throws IOException {

        System.out.println();

        Caricature picture = caricature;
        Optional.ofNullable(picture.getName()).ifPresent(n->picture.setPath("/global/caricature/upload/" + n));
        if(!file.isEmpty()){
            File path = new File(ResourceUtils.getURL("classpath:").getPath());
            File upload = new File(path.getAbsolutePath(), MessageFormat.format("{0}{1}", "static", picture.getPath()));
            if (!upload.exists()) {
                upload.mkdirs();
            }
            // file.transferTo(new File(upload, picture.getName()+file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("/")+1)));
            file.transferTo(new File(upload, picture.getName()+file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."))));
            file.getOriginalFilename();
        }
        int a = caricatureService.insertOrUpdate(picture);
        return "redirect:/caricature/manage/picture_list";
    }

    /**
     * picture编辑
     *
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
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/picture_del/{id}")
    public String pictureDel(@PathVariable("id") Long id) {
        caricatureService.caricatureDel(id);
        return "redirect:/caricature/manage/picture_list";
    }

    /**
     * 打印出操作
     *
     * @param model
     * @return
     */
    @GetMapping(value = "/action_list")
    public String actionList(Model model) {
        List<OperationLog> operationLog = operationLogService.seleteAll();
        model.addAttribute(operationLog);
        return "action_list";
    }

    @GetMapping(value = "/caricature_form")
    public String caricatureForm(Model model, @Value("#{genreCaricatureServiceImpl.selectAll()}") List<GenreCaricature> genreCaricatureServiceList) {
        List<Caricature> caricatureList = caricatureService.selectAll();
        Map<String, Long> caricatureNameMap = caricatureList.stream().collect(Collectors.toMap(Caricature::getName, Caricature::getId, (key1, key2) -> key2));
        Map<Long, String> genreCaricatureServiceNameMap = genreCaricatureServiceList.stream().collect(Collectors.toMap(GenreCaricature::getId, GenreCaricature::getGenre));

        model.addAttribute("caricatureNameMap", caricatureNameMap);
        model.addAttribute("genreCaricatureServiceNameMap", genreCaricatureServiceNameMap);
        model.addAttribute(new Caricature());
        return "caracture_form";
    }

    @Autowired
    ExtractZip extractZip;

    /**
     * caricature表单上传， file的上传
     *
     * @param file
     * @param caricature
     * @return
     */
    @PostMapping(value = "/caricature_form")
    public String caricaturePut(@RequestPart("fileCaracture") MultipartFile file, @Validated Caricature caricature) throws FileNotFoundException {
        // 稍后处理
        Caricature caricature1 = caricature;
        String regEx = "^(?<id>\\d+)-(?<name>.*)$";
        Pattern pattern = Pattern.compile(regEx);
        File path = new File(ResourceUtils.getURL("classpath:").getPath());

        Optional.ofNullable(caricature1.getName()).map(n -> pattern.matcher(n)).filter(Matcher::matches).ifPresent(m -> {
            caricature1.setId(Long.parseUnsignedLong(m.group("id")));
            caricature1.setName(m.group("name"));
            // "/global/caricature/upload/" + caricature1.getName()
            Optional.ofNullable(file).filter(f -> !f.isEmpty()).map(f -> file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf("."))).ifPresent(n -> caricature1.setUrl("/global/caricature/upload/" + caricature1.getName() + File.separator + n));

            Predicate<Integer> p = (result) -> {
                return result > 0;
            };

            if (p.test(caricatureService.insertOrUpdate(caricature1))) {
                File saveDirs = new File(path.getAbsolutePath(), MessageFormat.format("{0}{1}", "static", caricatureService.getCaricature(caricature1.getId()).getPath()));
                // 路径不存在追加
                if (!saveDirs.exists()) {
                    saveDirs.mkdirs();
                }
                new Thread(() -> {
                    FileUpload fileUpload = (caricatureFile, saveDir) -> {
                        try {
                            // File.separator
                            caricatureFile.transferTo(new File(saveDir.getAbsolutePath(), caricatureFile.getOriginalFilename()));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    };
                    // File saveDir = new File(path.getAbsolutePath(), MessageFormat.format("{0}{1}", "static", caricatureService.getCaricature(caricature1.getId()).getPath()));
                    fileUpload.upload(file, saveDirs);

                    Map<String, String> fileMap = new HashMap<>(2);
                    // fileMap.put("E:\\download\\caricature\\upload\\一拳超人\\closing.zip", "E:\\download\\caricature\\upload\\一拳超人");
                    fileMap.put(saveDirs.getAbsolutePath() + File.separator + file.getOriginalFilename(), saveDirs.getAbsolutePath());
                    extractZip.unZip(fileMap);
                }).start();
            }

        });
        // extractZip.unZip(caricature1.getUrl() + File.separator + file.getOriginalFilename(), caricature1.getUrl());

        return "redirect:/caricature/manage/picture_list";
    }
}
