package fun.tomberg.swedbankhm.controller;

import fun.tomberg.swedbankhm.entity.User;
import fun.tomberg.swedbankhm.exception.ForbiddenException;
import fun.tomberg.swedbankhm.exception.UserNotFoundException;
import fun.tomberg.swedbankhm.repository.UserRepository;
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
@RequestMapping("/admin")
public class AdminController {

    private final UserServiceImpl userService;
    private final UserRepository userRepository;

    @GetMapping("")
    public String adminGet(Model model) {
        model.addAttribute("customers", userRepository.findAll());
        return "admin-view";
    }

    @PostMapping("")
    public String adminEditPost(@RequestParam("userId") int id) {
        return "redirect:/admin/" + id;
    }

    @GetMapping("/{id}")
    public String adminEditGet(@PathVariable("id") int id, Model model) throws UserNotFoundException {
        if (userRepository.findById(id).isPresent()) {
             if (userRepository.findById(id).get().isAdmin()) {
                 throw new ForbiddenException("You cannot edit admin profiles!");
             } else {
                 model.addAttribute("user", userRepository.findById(id).get());
             }
        } else {
            throw new UserNotFoundException("User with id " + id + " not found");
        }
        return "admin-editing";
    }

    @PostMapping("/{id}")
    public ModelAndView adminEditPost(@Valid @ModelAttribute("user") User user,
                                BindingResult bindingResult,
                                @PathVariable("id") int id,
                                ModelMap model,
                                @RequestParam("action") String action) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("admin-editing", model);
        }
        user.setEnabled(userRepository.findById(id).get().isEnabled());
        if (action.equals("switch")) {
            model.addAttribute("user", userService.adminSaving(userRepository.findById(id).get().adminEditing(userService.switchUserStatus(user, bindingResult, id)), user));
            model.addAttribute("errors", bindingResult.getAllErrors());
            return new ModelAndView("admin-editing", model);
        }
        if (action.equals("submit")) {
            userService.adminSaving(userRepository.findById(id).get().adminEditing(user), user);
            return new ModelAndView("redirect:/admin", model);
        }
        return new ModelAndView("redirect:/admin", model);
    }
}
