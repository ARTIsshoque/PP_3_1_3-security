package org.example1.security.web;

import org.example1.security.model.User;
import org.example1.security.service.EntityService;
import org.example1.security.service.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("admin/")
public class MainController {

    private final EntityService<User> userService;
    private final RoleService roleService;

    public MainController(EntityService<User> userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/")
    public String getIndex(ModelMap model) {
        model.addAttribute("users", userService.findAll());
        return "index";
    }

    @GetMapping("add")
    public String getAdd(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.findAll());
        return "add";
    }

    @PostMapping("add")
    public String postAdd(@ModelAttribute("user") User user) {
        userService.add(user);
        return "redirect:/admin/";
    }

    @PostMapping("delete")
    public String postDelete(@RequestParam("id") Long id) {
        userService.delete(id);
        return "redirect:/admin/";
    }

    @GetMapping("edit")
    public String getEdit(@RequestParam("id") Long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        model.addAttribute("roles", roleService.findAll());
        return "edit";
    }

    @PostMapping("edit")
    public String postEdit(@ModelAttribute("user") User user) {
        userService.update(user);
        return "redirect:/admin/";
    }
}
