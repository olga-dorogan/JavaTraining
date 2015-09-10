package com.custom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by olga on 09.05.15.
 */
@Controller
public class AppController {
    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public ModelAndView welcomePage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("title", "Spring sec&mvc");
        modelAndView.addObject("message", "Welcome page");
        modelAndView.setViewName("welcome");
        return modelAndView;
    }

    @RequestMapping(value = "/protected", method = RequestMethod.GET)
    public ModelAndView protectedPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("title", "Spring protected");
        modelAndView.addObject("message", "Protected page");
        modelAndView.setViewName("protected");
        return modelAndView;
    }

    @RequestMapping(value = "/confidential", method = RequestMethod.GET)
    public ModelAndView confidentialPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("title", "Spring confidential");
        modelAndView.addObject("message", "Confidential page");
        modelAndView.setViewName("protected");
        return modelAndView;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout) {

        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("error", "Invalid username and password!");
        }

        if (logout != null) {
            model.addObject("msg", "You've been logged out successfully.");
        }
        model.setViewName("login");

        return model;

    }
}
