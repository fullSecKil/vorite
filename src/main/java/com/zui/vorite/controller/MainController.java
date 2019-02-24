package com.zui.vorite.controller;


import com.zui.vorite.pojo.Caricature;
import com.zui.vorite.service.CaricatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author Dusk
 */
@Controller
@RequestMapping("/test")
public class MainController {

    private CaricatureService caricatureService;

    @Autowired
    void setCaricatureService(CaricatureService caricatureService) {
        this.caricatureService = caricatureService;
    }

    @GetMapping("/test")
    public String test(){
        return "cp/index";
    }

    @GetMapping("/testGroup")
    public String testGroup(Model model){
        List<Caricature> caricatureList = caricatureService.selectAll();
        model.addAttribute(caricatureList);
        return "cp/share";
    }

}
