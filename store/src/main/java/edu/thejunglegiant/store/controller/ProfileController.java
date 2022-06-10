package edu.thejunglegiant.store.controller;

import edu.thejunglegiant.store.data.entity.UserEntity;
import edu.thejunglegiant.store.security.JwtTokenProvider;
import edu.thejunglegiant.store.security.UserRole;
import edu.thejunglegiant.store.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String profile(HttpServletRequest request, Model model) {
        UserEntity user = userService.getUser(request);

        model.addAttribute("user", user);
        model.addAttribute("isAdmin", user.getUserRole() == UserRole.ADMIN);

        return "profile/profile";
    }

    @GetMapping("/list")
    public String profilesList(HttpServletRequest request, Model model) {
        model.addAttribute("users", userService.getAllUsers(request));
        return "profile/list";
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("success");
    }
}
