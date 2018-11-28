package com.zui.vorite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Dusk
 */
@Controller
public class MainController {
    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
