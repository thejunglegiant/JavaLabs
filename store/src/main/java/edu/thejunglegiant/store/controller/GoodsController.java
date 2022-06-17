package edu.thejunglegiant.store.controller;

import edu.thejunglegiant.store.data.entity.GoodEntity;
import edu.thejunglegiant.store.data.entity.UserEntity;
import edu.thejunglegiant.store.dto.CatalogFilterDTO;
import edu.thejunglegiant.store.security.JwtTokenProvider;
import edu.thejunglegiant.store.security.UserRole;
import edu.thejunglegiant.store.service.GoodsService;
import edu.thejunglegiant.store.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
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
    public String showCatalog(@ModelAttribute CatalogFilterDTO filters, HttpServletRequest request, Model model) {
        List<GoodEntity> goods;

        if (filters == null)
            goods = service.getAllGoods();
        else {
            goods = service.getFilteredGoods(filters);
        }

        String token = JwtTokenProvider.resolveToken(request);
        String email = JwtTokenProvider.getUserEmail(token);

        UserEntity user = userService.getUser(email);

        model.addAttribute("filters", filters == null ? new CatalogFilterDTO() : filters);
        model.addAttribute("goods", goods);
        model.addAttribute("categories", service.getAllCategories());
        model.addAttribute("isRegistered", user != null);
        model.addAttribute("isAdmin", user != null && user.getUserRole() == UserRole.ADMIN);
        return "catalog/catalog";
    }

    @GetMapping("/{id}")
    public String showGoodInfo(@PathVariable("id") int id, HttpServletRequest request, Model model) {
        String token = JwtTokenProvider.resolveToken(request);
        String email = JwtTokenProvider.getUserEmail(token);

        model.addAttribute("good", service.getGoodById(id));
        model.addAttribute("isAdmin", userService.getUser(email).getUserRole() == UserRole.ADMIN);
        return "catalog/good";
    }


}
