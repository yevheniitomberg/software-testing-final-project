package fun.tomberg.swedbankhm.controller;

import fun.tomberg.swedbankhm.entity.User;
import fun.tomberg.swedbankhm.service.implementation.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class RegistrationController {

    private final UserServiceImpl userService;

    @GetMapping("/register")
    public String registerGet(Model model) {
        model.addAttribute("user", new User());
        return "register-view";
    }

    @PostMapping("/register")
    public ModelAndView registerPost(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, ModelMap model) {
        if (userService.getErrors("registration", user, bindingResult).hasErrors()) {
            return new ModelAndView("register-view", model);
        }
        userService.saveUser(user);
        model.addAttribute("regSuccess", "User was successfully created!");
        return new ModelAndView("forward:/", model);
    }
}
