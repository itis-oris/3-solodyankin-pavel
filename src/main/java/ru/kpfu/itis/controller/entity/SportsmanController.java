package ru.kpfu.itis.controller.entity;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.kpfu.itis.dto.entity.coach.CoachAddAndDeleteDto;
import ru.kpfu.itis.dto.entity.sportsman.SportsmanUpdateDto;
import ru.kpfu.itis.model.Coach;
import ru.kpfu.itis.model.GroupSportsman;
import ru.kpfu.itis.model.Sportsman;
import ru.kpfu.itis.model.User;
import ru.kpfu.itis.security.CustomUserDetails;
import ru.kpfu.itis.service.interfaces.*;

import java.util.List;

@Controller
@RequestMapping("/electron/sportsman")
@RequiredArgsConstructor
@Slf4j
public class SportsmanController {

    private final SportsmanService sportsmanService;
    private final CoachService coachService;
    private final GroupSportsmanService groupSportsmanService;


    @GetMapping("/home")
    public String sportsmanHome(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userDetails.getUser();
        if (user instanceof Sportsman sportsman) {
            model.addAttribute("user", sportsman);
        }
        return "sportsman/home";
    }

    @GetMapping("/update")
    public String getPageUpdateProfile(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userDetails.getUser();
        if (!model.containsAttribute("user")) {
            if (user instanceof Sportsman sportsman) {
                model.addAttribute("user", sportsman);
            }
        }

        return "sportsman/update-profile";
    }

    @PutMapping("/update")
    public String updateProfile(@AuthenticationPrincipal CustomUserDetails userDetails,
                                @ModelAttribute("sportsman") @Valid SportsmanUpdateDto updatedSportsman,
                                BindingResult result,
                                RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", result);
            redirectAttributes.addFlashAttribute("user", updatedSportsman);
            return "redirect:/electron/sportsman/update";
        }

        User user = userDetails.getUser();
        if (user instanceof Sportsman sportsman) {
            sportsman.setName(updatedSportsman.getName());
            sportsman.setAge(updatedSportsman.getAge());
            sportsman.setPhone(updatedSportsman.getPhone());
            sportsman.setAddress(updatedSportsman.getAddress());
            sportsman.setRank(updatedSportsman.getRank());

            sportsmanService.updateSportsman(sportsman);
            redirectAttributes.addFlashAttribute("success", "Данные успешно обновлены");
        }
        return "redirect:/electron/sportsman/home";
    }

    @GetMapping("/coaches")
    public String getPageCoaches(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userDetails.getUser();
        if (user instanceof Sportsman sportsman) {
            Sportsman sportsmanWithCoaches = sportsmanService.getSportsmanWithCoaches(sportsman.getId());
            List<Coach> coaches = sportsmanWithCoaches.getCoaches();
            List<Coach> availableCoaches = coachService.findAllNotInCoaches(sportsmanWithCoaches);

            model.addAttribute("coaches", coaches);
            model.addAttribute("availableCoaches", availableCoaches);
            model.addAttribute("user", sportsmanWithCoaches);
        }
        return "sportsman/my-coaches";
    }

    @PostMapping("/coaches")
    public String addCoaches(@ModelAttribute CoachAddAndDeleteDto dto,
                             RedirectAttributes redirectAttributes) {
        if (dto.getSportsmanId() != null && dto.getCoachIds() != null) {
            coachService.addCoachesToSportsman(dto.getSportsmanId(), dto.getCoachIds());
            redirectAttributes.addFlashAttribute("success", "Тренеры добавлены");
        }
        else {
            redirectAttributes.addFlashAttribute("error", "Не удалось добавить тренеров");
        }return "redirect:/electron/sportsman/coaches";
    }

    @DeleteMapping("/coaches")
    public String deleteCoach(@ModelAttribute CoachAddAndDeleteDto dto,
                              RedirectAttributes redirectAttributes) {
        if (dto.getSportsmanId() != null && dto.getCoachIds() != null) {
            coachService.removeCoachesFromSportsman(dto.getSportsmanId(), dto.getCoachIds());
            redirectAttributes.addFlashAttribute("success", "Тренеры удалены");
        }
        else {
            redirectAttributes.addFlashAttribute("error", "Не удалось удалить тренера");
        }
    return "redirect:/electron/sportsman/coaches";
    }

    @GetMapping("/groups")
    public String getPageGroups(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userDetails.getUser();
        if (user instanceof Sportsman sportsman) {
            List<GroupSportsman> groups = groupSportsmanService.findBySportsman(sportsman);
            model.addAttribute("groups", groups);
        }
        return "sportsman/groups-sportsman";
    }

    @GetMapping("/trainings")
    public String getPageTrainings(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userDetails.getUser();
        if (user instanceof Sportsman sportsman) {
            List<GroupSportsman> groups = groupSportsmanService.findBySportsman(sportsman);
            model.addAttribute("groups", groups);
        }
        return "sportsman/training-sportsman";
    }
}