package edu.thejunglegiant.store.controller;

import edu.thejunglegiant.store.security.UserRole;
import edu.thejunglegiant.store.service.GoodsService;
import edu.thejunglegiant.store.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequestMapping("/catalog")
public class GoodsController {

    private final GoodsService service;
    private final UserService userService;

    public GoodsController(GoodsService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    @GetMapping
    public String fetchAllGoods(
            @RequestParam(value = "sortBy", required = false, defaultValue = "0") int sortBy,
            @RequestParam(value = "from", required = false, defaultValue = "-1") int from,
            @RequestParam(value = "to", required = false, defaultValue = "-1") int to,
            Model model
    ) {
        model.addAttribute("goods", service.getAllGoods(sortBy, from, to));
        return "catalog/catalog";
    }

    @GetMapping("/{id}")
    public String showGoodInfo(@PathVariable("id") int id, HttpServletRequest request, Model model) {
        model.addAttribute("good", service.getGoodById(id));
        model.addAttribute("isAdmin", userService.getUserRoleFromRequest(request) == UserRole.ADMIN);
        return "catalog/good";
    }


}
