package org.example1.security.configs;

import org.example1.security.model.Role;
import org.example1.security.model.User;
import org.example1.security.service.RoleService;
import org.example1.security.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/user").setViewName("user");
    }

    @Bean
    public CommandLineRunner dataLoader(UserService userService, RoleService roleService) {
        return args -> {
            Role adminRole = roleService.findByName("ADMIN").orElse(new Role("ADMIN"));
            Role userRole = roleService.findByName("USER").orElse(new Role("USER"));

            List<User> users = userService.findAllWithRoles();
            boolean adminPresent = false;
            for (User user : users) {
                if (user.getRoles().stream().anyMatch(role -> role.getName().equals("ADMIN"))) {
                    adminPresent = true;
                }
            }
            if (!adminPresent) {
                User admin = new User("admin", "Иван", "Иванов",
                        "ivanov@company.com", "47474747",
                        "admin",
                        Arrays.asList(adminRole, userRole));
                userService.add(admin);
            }
        };
    }
}
