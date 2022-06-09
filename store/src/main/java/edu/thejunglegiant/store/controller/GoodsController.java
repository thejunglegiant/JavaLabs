package edu.thejunglegiant.store.controller;

import edu.thejunglegiant.store.data.entity.CategoryEntity;
import edu.thejunglegiant.store.data.entity.GoodEntity;
import edu.thejunglegiant.store.dto.CatalogFilterDTO;
import edu.thejunglegiant.store.security.UserRole;
import edu.thejunglegiant.store.service.GoodsService;
import edu.thejunglegiant.store.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

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
    public String showCatalog(@ModelAttribute CatalogFilterDTO filters, Model model) {
        List<GoodEntity> goods;

        if (filters == null)
            goods = service.getAllGoods();
        else {
            goods = service.getFilteredGoods(filters);
        }

        model.addAttribute("filters", filters == null ? new CatalogFilterDTO() : filters);
        model.addAttribute("goods", goods);
        List<CategoryEntity> list = service.getAllCategories();
        model.addAttribute("categories", service.getAllCategories());
        return "catalog/catalog";
    }

    @GetMapping("/{id}")
    public String showGoodInfo(@PathVariable("id") int id, HttpServletRequest request, Model model) {
        model.addAttribute("good", service.getGoodById(id));
        model.addAttribute("isAdmin", userService.getUserRoleFromRequest(request) == UserRole.ADMIN);
        return "catalog/good";
    }


}
