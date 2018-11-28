package com.zui.vorite.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/uploads")
public class FileUploadController {

    private static final Logger log = LoggerFactory.getLogger(FileUploadController.class);

    @GetMapping
    public String upload(){
        return "upload";
    }

    @PostMapping("/upload1")
    @ResponseBody
    public Map<String, String> upload1(@RequestParam("file") MultipartFile file) throws IOException {
        log.info("[文件类型] - [{}]", file.getContentType());
        log.info("[文件名称] - [{}]", file.getOriginalFilename());
        log.info("[文件大小] - [{}]", file.getSize());

        file.transferTo(new File(file.getOriginalFilename()));
        Map<String, String> result = new HashMap<>(16);
        result.put("contentType", file.getContentType());
        result.put("fileName", file.getOriginalFilename());
        result.put("fileSize", file.getSize()+"");
        return result;
    }

    @PostMapping("/upload2")
    @ResponseBody
    public List<Map<String, String>> upload2(@RequestParam("file") MultipartFile[] files){
        if(files == null || files.length == 0){
            return null;
        }

        List<Map<String, String>> results = new ArrayList<>();
        Arrays.stream(files).forEach(f->{
            try {
                f.transferTo(new File("b\\"+f.getOriginalFilename()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Map<String, String> map = new HashMap<>(16);
            map.put("contentType", f.getContentType());
            map.put("fileName", f.getOriginalFilename());
            map.put("fileSize", f.getSize()+"");
            results.add(map);
        });
        return results;
    }
}
