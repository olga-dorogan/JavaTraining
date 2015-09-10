package com.custom;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by olga on 09.05.15.
 */
@Controller
@RequestMapping("/")
public class HomeController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String printWelcome(ModelMap model) {
        model.addAttribute("from", "SimpleController");
        return "hello";
    }

    @RequestMapping(value = "time", method = RequestMethod.GET)
    public String printTime(Locale locale, Model model) {
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
        String formattedDate = dateFormat.format(new Date());
        model.addAttribute("time", formattedDate);
        return "time";
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginUser() {
        return "login";
    }

    @RequestMapping(value = "greeting", method = RequestMethod.POST)
    public String greeting(User user, Model model) {
        model.addAttribute("user", user.getUserName());
        return "greeting";
    }
}
