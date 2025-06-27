package ru.kpfu.itis.controller.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.kpfu.itis.exception.*;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public String handleEmailAlreadyExist(EmailAlreadyExistsException ex,
                                          RedirectAttributes redirectAttributes
                                          ) {
        redirectAttributes.addFlashAttribute("userRequest", ex.getUserRequest());
        redirectAttributes.addFlashAttribute("error", ex.getMessage());
        return "redirect:/electron/auth/register";
    }

    @ExceptionHandler(GroupSportsmanNotFoundException.class)
    public String handleGroupSportsmanNotFound(GroupSportsmanNotFoundException ex,
                                               RedirectAttributes redirectAttributes
    ) {
        redirectAttributes.addFlashAttribute("error", ex.getMessage());
        return "redirect:/electron/coach/groups";
    }

    @ExceptionHandler(SportsmanNotFoundException.class)
    public String handleSportsmanNotFound(SportsmanNotFoundException ex,
                                               RedirectAttributes redirectAttributes
    ) {
        redirectAttributes.addFlashAttribute("error", ex.getMessage());
        return "redirect:/electron/sportsman/coaches";
    }

    @ExceptionHandler(SportNotFoundException.class)
    public String handleSportNotFound(SportNotFoundException ex,
                                               RedirectAttributes redirectAttributes
    ) {
        redirectAttributes.addFlashAttribute("error", ex.getMessage());
        return "redirect:/electron/coach/update";
    }

    @ExceptionHandler(CoachNotFoundException.class)
    public String handleCoachNotFound(CoachNotFoundException ex,
                                               RedirectAttributes redirectAttributes
    ) {
        redirectAttributes.addFlashAttribute("error", ex.getMessage());
        return "redirect:/electron/sportsman/coaches";
    }

    @ExceptionHandler(ScheduleTrainingNotFoundException.class)
    public String handleScheduleTrainingNotFound(ScheduleTrainingNotFoundException ex,
                                               RedirectAttributes redirectAttributes
    ) {
        redirectAttributes.addFlashAttribute("error", ex.getMessage());
        return "redirect:/electron/coach/trainings";
    }

}
