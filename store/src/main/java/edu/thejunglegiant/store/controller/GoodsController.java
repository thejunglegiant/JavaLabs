package edu.thejunglegiant.store.controller;

import edu.thejunglegiant.store.data.dao.good.GoodsDao;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/catalog")
public class GoodsController {

    private static final GoodsDao dao = new GoodsDao();

    @GetMapping
    public ResponseEntity fetchAllGoods() {
        try {
            return ResponseEntity.ok(dao.fetchAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error happened");
        }
    }

    @GetMapping("/calculated")
    public String calculated(@RequestParam(value = "a") int a, @RequestParam(value = "b") int b, Model model) {
        model.addAttribute("result", a * b);
        return "hello_world";
    }
}
