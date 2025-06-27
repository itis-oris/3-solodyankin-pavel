package ru.kpfu.itis.controller.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.dto.request.ConfirmResetRequest;
import ru.kpfu.itis.dto.request.RequestResetRequest;
import ru.kpfu.itis.service.impl.security.EmailSmsClient;
import ru.kpfu.itis.service.interfaces.UserService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequestMapping("/electron/auth/password-reset")
@RequiredArgsConstructor
@Slf4j
public class PasswordResetController {

    private final UserService userService;
    private final EmailSmsClient emailSmsClient;

    private final Map<String, String> cache = new ConcurrentHashMap<>();

    @PostMapping("/request")
    public String handleRequestForm(@ModelAttribute RequestResetRequest dto, Model model) {
        String code = String.format("%06d", (int) (Math.random() * 1_000_000));
        cache.put(dto.getEmail(), code);
        emailSmsClient.sendCode(dto.getEmail(), code);
        model.addAttribute("success", "Код отправлен на почту");
        return "redirect:/electron/auth/password-reset/confirm";
    }

    @GetMapping("/confirm")
    public String showConfirmForm(Model model) {
        if (!model.containsAttribute("confirmResetRequest")) {
            model.addAttribute("confirmResetRequest", new ConfirmResetRequest());
        }
        return "auth/confirm-reset";
    }

    @PostMapping("/confirm")
    public String handleConfirmForm(@ModelAttribute ConfirmResetRequest dto, Model model) {
        String cachedCode = cache.get(dto.getEmail());
        log.info(cachedCode);
        if (!dto.getCode().equals(cachedCode)) {
            model.addAttribute("error", "Неверный код или email адрес");
            model.addAttribute("confirmResetRequest", dto);
            return "auth/confirm-reset";
        }

        userService.resetPassword(dto);
        model.addAttribute("success", "Пароль успешно изменён");
        return "redirect:/electron/auth/login";
    }
}
