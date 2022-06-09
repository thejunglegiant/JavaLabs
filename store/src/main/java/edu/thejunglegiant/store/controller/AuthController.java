package edu.thejunglegiant.store.controller;

import edu.thejunglegiant.store.service.AuthService;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @GetMapping("/login")
    private String login() {
        return "auth/login";
    }

    @PostMapping(path = "/login", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    private String authenticate(@RequestBody MultiValueMap<String, String> body, HttpServletResponse response) {
        try {
            String email = body.getFirst("email");
            String password = body.getFirst("password");

            String token = service.authorize(email, password);

            Cookie jwtCookie = new Cookie("Authorization", token);
            jwtCookie.setPath("/");
            jwtCookie.setMaxAge(24 * 60 * 60);
            jwtCookie.setHttpOnly(true);

            response.addCookie(jwtCookie);

            return "redirect:/catalog";
        } catch (AuthenticationException e) {
            return "Invalid email/password";
        }
    }

    @PostMapping("/logout")
    private void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler handler = new SecurityContextLogoutHandler();
        handler.logout(request, response, null);
    }
}
