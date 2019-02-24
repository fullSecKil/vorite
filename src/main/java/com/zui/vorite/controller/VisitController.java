package com.zui.vorite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @file: VisitController.class
 * @author: Dusk
 * @since: 2019/2/23 17:07
 * @desc: 浏览管理
 */

@Controller()
@RequestMapping("/visit")
public class VisitController {

    @GetMapping("/global/caricature/upload/{name}")
    @ResponseBody
    public String visitCaricature(@RequestParam("caricatureId") Long id, @PathVariable("name") String name){
        return name+id;
    }
}
