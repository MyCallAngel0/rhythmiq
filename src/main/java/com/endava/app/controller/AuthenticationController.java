package com.endava.app.controller;

import com.endava.app.domain.User;
import com.endava.app.model.request.AuthenticationRequest;
import com.endava.app.model.request.RegisterRequest;
import com.endava.app.model.response.AuthenticationResponse;
import com.endava.app.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authService;

    @GetMapping("/register")
    public String showRegistrationForm(final Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }

    @PostMapping("api/register")
    public String register(
            @ModelAttribute RegisterRequest request,
            RedirectAttributes redirectAttributes
    ) {
        try {
            AuthenticationResponse token = authService.register(request);
            redirectAttributes.addFlashAttribute("Authorization", "Bearer " + token.getToken());
            log.info("User {} successfully registered!", request.getUsername());
            return "redirect:/";
        } catch (Exception e) {
            log.error("Failed to register: " + e.getMessage());
            return "error";
        }

    }

    @GetMapping("/login")
    public String showLoginForm(final Model model) {
        model.addAttribute("user", new User());
        return "auth/login";
    }

    @PostMapping("api/login")
    public String login(
            @ModelAttribute AuthenticationRequest request
    ) {
        try {
            AuthenticationResponse token = authService.login(request);
            log.info("User {} successfully logged!", request.getUsername());
            return "redirect:/";
        } catch (Exception e) {
            log.error("Failed to login: " + e.getMessage());
            return "error";
        }
    }
}
