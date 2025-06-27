package ru.kpfu.itis.controller.entity;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/electron")
public class HomeController {

    @GetMapping("/home")
    public String getHomePage() {
        return "user/home";
    }
    @GetMapping("/user")
    public String getUsersPage() {
        return "user/users";
    }
}
