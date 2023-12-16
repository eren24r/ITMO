package org.web4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MVCController {

    @GetMapping("/ker")
    public String doKer(){
        return "lab4";
    }

}
