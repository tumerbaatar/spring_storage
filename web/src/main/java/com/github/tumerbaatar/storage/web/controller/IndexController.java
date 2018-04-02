package com.github.tumerbaatar.storage.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String showIndex() {
        return "shop";
    }

    @GetMapping("/storage")
    public String storage() {
        return "storage";
    }

}
