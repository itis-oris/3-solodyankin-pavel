package ru.kpfu.itis.controller.security;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import ru.kpfu.itis.model.Coach;
import ru.kpfu.itis.model.Sportsman;
import ru.kpfu.itis.model.User;
import ru.kpfu.itis.security.CustomUserDetails;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model,@AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = null;
        if (userDetails != null) {
            user = userDetails.getUser();
        }
        String referer = request.getHeader("Referer");
        if (referer == null && user != null) {
            if (user instanceof Coach){
                referer = "/electron/coach/home";
            } else if (user instanceof Sportsman){
                referer = "/electron/sportsman/home";
            } else referer = "/electron/home";
        }

        model.addAttribute("referer", referer);

        Integer statusCode = (Integer) request.getAttribute("error.status_code");
        if (statusCode == null) {
            statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        }

        if (statusCode != null) {
            switch (statusCode) {
                case 400: return "errors/400";
                case 401:
                    model.addAttribute("referer", "/electron/auth/login");
                    return "errors/401";
                case 403: return "errors/403";
                case 404: return "errors/404";
                case 405: return "errors/405";
                case 500: return "errors/500";
                default:
                    model.addAttribute("status", statusCode);
                    model.addAttribute("message", "Неизвестная ошибка");
                    return "errors/error";
            }
        }

        model.addAttribute("message", "Произошла ошибка");
        return "errors/error";
    }
}