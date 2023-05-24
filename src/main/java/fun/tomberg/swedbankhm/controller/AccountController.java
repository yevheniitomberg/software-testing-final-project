package fun.tomberg.swedbankhm.controller;

import fun.tomberg.swedbankhm.entity.User;
import fun.tomberg.swedbankhm.service.implementation.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {

    private final UserServiceImpl userService;

    @GetMapping("")
    public String accountGet(Model model) {
        model.addAttribute("user", userService.getCurrentUser());
        return "account-view";
    }

    @PostMapping("")
    public String accountPost(@RequestParam("action") String action) {
        if (action.equals("delete_account")) {
            userService.disableUser(userService.getCurrentUser());
            return "redirect:/logout";
        } else if (action.equals("update")) {
            return "redirect:/account/edit-info";
        } else {
            return "redirect:/account";
        }
    }

    @GetMapping("/edit-info")
    public String editInfoGet(Model model) {
        model.addAttribute("user", userService.getCurrentUser());
        return "edit-info-view";
    }

    @PostMapping("/edit-info")
    public ModelAndView editInfoPost(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, ModelMap model) {
        if (userService.getErrors("", user, bindingResult).hasErrors()) {
            return new ModelAndView("edit-info-view", model);
        }
        userService.editUser(user);
        return new ModelAndView("redirect:/account", model);
    }

    @GetMapping("/test-report")
    public String getTestReport() {
        System.out.println(System.getProperty("user.dir"));
        return "reports/emailable-report";
    }
}
