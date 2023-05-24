package fun.tomberg.swedbankhm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomepageController {

    @PostMapping("/")
    public String homepagePost() {
        return "homepage-view";
    }

    @GetMapping("/")
    public String homepageGet() {
        return "homepage-view";
    }
}
