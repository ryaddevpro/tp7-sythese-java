package com.example.demo.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ChefController {

    @GetMapping("/ChefPagw")
    public String adminPage() {
        return "Project/ChefPagw"; // Retourne le fichier "adminPage.html" dans src/main/resources/templates
    }





}
