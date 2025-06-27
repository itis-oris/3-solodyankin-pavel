package ru.kpfu.itis.controller.security;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.kpfu.itis.dto.request.AuthRequest;
import ru.kpfu.itis.dto.request.RequestResetRequest;
import ru.kpfu.itis.dto.request.UserRequest;
import ru.kpfu.itis.model.Coach;
import ru.kpfu.itis.model.Sportsman;
import ru.kpfu.itis.model.User;
import ru.kpfu.itis.security.CustomUserDetails;
import ru.kpfu.itis.service.interfaces.UserService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/electron/auth")
@Slf4j
public class AuthController {

    private final UserService userService;

    @GetMapping("/register")
    public String getRegisterPage(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails != null) {
            User user = userDetails.getUser();
            if (user instanceof Sportsman) {
                return "redirect:/electron/sportsman/home";
            }
            else if (user instanceof Coach) {
                return "redirect:/electron/coach/home";
            }
        }
        if (!model.containsAttribute("userRequest")) {
            model.addAttribute("userRequest", new UserRequest());
        }
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("userRequest") @Valid UserRequest userRequest,
                           BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("userRequest", userRequest);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRequest", result);
            return "redirect:/electron/auth/register";
        }
        userService.register(userRequest);
        return "redirect:/electron/auth/login?success";

    }

    @GetMapping("/login")
    public String getLoginPage(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails != null) {
            User user = userDetails.getUser();
            if (user instanceof Sportsman) {
                return "redirect:/electron/sportsman/home";
            }
            else if (user instanceof Coach) {
                return "redirect:/electron/coach/home";
            }

        }
        if (!model.containsAttribute("authRequest")) {
            model.addAttribute("authRequest", new AuthRequest());
        }
        model.addAttribute("requestResetRequest", new RequestResetRequest());
        return "auth/login";
    }

}
