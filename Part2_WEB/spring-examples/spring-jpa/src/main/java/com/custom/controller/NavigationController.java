package com.custom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by olga on 14.05.15.
 */
@Controller
public class NavigationController {
    @RequestMapping(value = {"/", "home"}, method = RequestMethod.GET)
    public String homePage() {
        return "index";
    }
}
