package edu.thejunglegiant.store.controller;

import edu.thejunglegiant.store.security.JwtTokenProvider;
import edu.thejunglegiant.store.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/cart")
@Slf4j
public class CartController {

    private final CartService cartService;

    public CartController(CartService service) {
        this.cartService = service;
    }

    @GetMapping
    public String showCart(HttpServletRequest request, Model model) {
        String token = JwtTokenProvider.resolveToken(request);
        String email = JwtTokenProvider.getUserEmail(token);

        model.addAttribute("goods", cartService.showCart(email));

        return "cart/cart";
    }

    @PostMapping
    public String payForCart(HttpServletRequest request) {
        String token = JwtTokenProvider.resolveToken(request);
        String email = JwtTokenProvider.getUserEmail(token);

        cartService.makeCartPayment(email);

        return "cart/success";
    }

    @DeleteMapping("/{id}")
    public ResponseEntity removeItemFromCart(@PathVariable("id") int id, HttpServletRequest request) {
        String token = JwtTokenProvider.resolveToken(request);
        String email = JwtTokenProvider.getUserEmail(token);

        cartService.removeGoodFromCart(email, id);

        return ResponseEntity.ok("success");
    }

    @PutMapping
    public ResponseEntity addItemToCart(@RequestParam("goodId") int id, HttpServletRequest request) {
        String token = JwtTokenProvider.resolveToken(request);
        String email = JwtTokenProvider.getUserEmail(token);

        cartService.addGoodToCart(email, id);

        return ResponseEntity.ok("success");
    }
}
