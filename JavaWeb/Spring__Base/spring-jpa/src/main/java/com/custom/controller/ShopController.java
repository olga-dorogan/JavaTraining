package com.custom.controller;

import com.custom.entity.Shop;
import com.custom.exception.ShopNotFound;
import com.custom.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Created by olga on 14.05.15.
 */
@Controller
@RequestMapping("/shop")
public class ShopController {
    @Autowired
    private ShopService shopService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView shopListPage() {
        ModelAndView mav = new ModelAndView("shop-list");
        List<Shop> shopList = shopService.findAll();
        mav.addObject("shopList", shopList);
        mav.addObject("listSize", shopList.size());
        return mav;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView createNewShop(@ModelAttribute Shop shop,
                                      final RedirectAttributes redirectAttributes) {

        ModelAndView mav = new ModelAndView();
        String message = "New shop " + shop.getName() + " was successfully created.";

        shopService.create(shop);
        mav.setViewName("redirect:/home.html");

        redirectAttributes.addFlashAttribute("message", message);
        return mav;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView shopCreatePage() {
        ModelAndView mav = new ModelAndView("shop-new", "shop", new Shop());
        return mav;
    }
    @RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
    public ModelAndView editShopPage(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView("shop-edit");
        Shop shop = shopService.findById(id);
        mav.addObject("shop", shop);
        return mav;
    }

    @RequestMapping(value="/edit/{id}", method=RequestMethod.POST)
    public ModelAndView editShop(@ModelAttribute Shop shop,
                                 @PathVariable Integer id,
                                 final RedirectAttributes redirectAttributes) throws ShopNotFound {

        ModelAndView mav = new ModelAndView("redirect:/home.html");
        String message = "Shop was successfully updated.";

        shopService.update(shop);

        redirectAttributes.addFlashAttribute("message", message);
        return mav;
    }

    @RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
    public ModelAndView deleteShop(@PathVariable Integer id,
                                   final RedirectAttributes redirectAttributes) throws ShopNotFound {

        ModelAndView mav = new ModelAndView("redirect:/home.html");

        Shop shop = shopService.delete(id);
        String message = "The shop "+shop.getName()+" was successfully deleted.";

        redirectAttributes.addFlashAttribute("message", message);
        return mav;
    }
}
