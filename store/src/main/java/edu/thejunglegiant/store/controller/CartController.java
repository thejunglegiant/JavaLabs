package edu.thejunglegiant.store.controller;

import edu.thejunglegiant.store.security.JwtTokenProvider;
import edu.thejunglegiant.store.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/cart")
@Slf4j
public class CartController {

    private final CartService cartService;

    public CartController(CartService service) {
        this.cartService = service;
    }

    @PutMapping
    public ResponseEntity addItemToCart(@RequestParam("goodId") int id, HttpServletRequest request) {
        String token = JwtTokenProvider.resolveToken(request);
        String email = JwtTokenProvider.getUserEmail(token);

        cartService.addGoodToCart(email, id);

        return ResponseEntity.ok("success");
    }
}
