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
import ru.kpfu.itis.dto.entity.coach.CoachUpdateDto;
import ru.kpfu.itis.dto.entity.group.GroupCreateDto;
import ru.kpfu.itis.dto.entity.group.GroupDeleteDto;
import ru.kpfu.itis.dto.entity.group.GroupWithTrainingsDto;
import ru.kpfu.itis.dto.entity.sportsman.SportsmanGroupUpdateDto;
import ru.kpfu.itis.dto.entity.training.TrainingCreateDto;
import ru.kpfu.itis.dto.entity.training.TrainingDeleteDto;
import ru.kpfu.itis.model.*;
import ru.kpfu.itis.security.CustomUserDetails;
import ru.kpfu.itis.service.interfaces.*;

import java.util.List;


@Controller
@RequestMapping("/electron/coach")
@RequiredArgsConstructor
@Slf4j
public class CoachController {

    private final CoachService coachService;
    private final SportService sportService;
    private final ScheduleTrainingService scheduleTrainingService;
    private final GroupSportsmanService groupSportsmanService;
    private final SportsmanService sportsmanService;

    @GetMapping("/home")
    public String coachHome(Model model, @AuthenticationPrincipal CustomUserDetails userDetails ) {
        User user = userDetails.getUser();

        if (user instanceof Coach coach){
            model.addAttribute("user", coach);
            model.addAttribute("sport", coach.getSport());
        }

        return "coach/home";
    }

    @GetMapping("/update")
    public String getPageUpdate(Model model, @AuthenticationPrincipal CustomUserDetails userDetails ) {
        User user = userDetails.getUser();
        CoachUpdateDto coachUpdateDto = null;

        if (!model.containsAttribute("user")) {
            if (user instanceof Coach coach) {
                model.addAttribute("user", coach);
            }
        } else{
            coachUpdateDto = (CoachUpdateDto) model.getAttribute("user");
        }

        List<Sport> sports = sportService.findAll();

        if (user instanceof Coach coach){
            String sport = coachUpdateDto != null ? coachUpdateDto.getSport().getName() : coach.getSport().getName();
            model.addAttribute("sports", sports);
            model.addAttribute("selectedSport", sport);
        }

        return "coach/update-profile";
    }

    @PutMapping("/update")
    public String updateProfile(@AuthenticationPrincipal CustomUserDetails userDetails,
                                @ModelAttribute("coach") @Valid CoachUpdateDto updatedCoach,
                                BindingResult result,
                                RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", result);
            redirectAttributes.addFlashAttribute("user", updatedCoach);
            return "redirect:/electron/coach/update";
        }

        User user = userDetails.getUser();

        if (user instanceof Coach coach){
            coach.setName(updatedCoach.getName());
            coach.setAddress(updatedCoach.getAddress());
            coach.setAge(updatedCoach.getAge());
            coach.setRank(updatedCoach.getRank());
            coach.setPhone(updatedCoach.getPhone());
            coach.setSport(updatedCoach.getSport());

            coachService.updateCoach(coach);
            redirectAttributes.addFlashAttribute("success", "Данные успешно обновлены");
        }
        return "redirect:/electron/coach/home";
    }

    @GetMapping("/trainings")
    public String getPageTrainings(Model model,
                                   @AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userDetails.getUser();
        if (user instanceof Coach coach) {
            List<GroupWithTrainingsDto> groups = scheduleTrainingService.getTrainingsByCoachGroups(coach);
            model.addAttribute("groups", groups);
        }
        return "coach/training-coach";
    }

    @GetMapping("/trainings/add")
    public String getPageAddTrainingForm(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userDetails.getUser();
        if (user instanceof Coach coach) {
            List<GroupSportsman> groups = groupSportsmanService.findByCoach(coach);
            model.addAttribute("groups", groups);
        }
        return "coach/add-training";
    }

    @PostMapping("/trainings/add")
    public String addTraining(@ModelAttribute TrainingCreateDto dto,
                              RedirectAttributes redirectAttributes) {
        if (dto.getGroupIds() != null && dto.getDay() != null && dto.getTime() != null) {
            for (Long groupId : dto.getGroupIds()) {
                if (scheduleTrainingService.existsByDayAndTimeAndGroup(dto.getDay(), dto.getTime(), groupId)) {
                    redirectAttributes.addFlashAttribute("error", "Тренировка '" + dto.getDay() + " " + dto.getTime() + "' уже существует для группы ID=" + groupId);
                }
                else{
                    scheduleTrainingService.createTraining(dto.getDay(), dto.getTime(), groupId);
                    redirectAttributes.addFlashAttribute("success", "Тренировка добавлена");
                }
            }
        }
        else {
            redirectAttributes.addFlashAttribute("error", "Не удалось добавить тренировку");
        }
        return "redirect:/electron/coach/trainings";
    }

    @DeleteMapping("/trainings")
    public String deleteTraining(@ModelAttribute TrainingDeleteDto dto,
                                 RedirectAttributes redirectAttributes) {
        if (dto.getTrainingId() != null) {
            scheduleTrainingService.deleteTraining(dto.getTrainingId());
            redirectAttributes.addFlashAttribute("success", "Тренировка удалена");
        }
        else {
            redirectAttributes.addFlashAttribute("error", "Не удалось удалить тренировку");
        }
        return "redirect:/electron/coach/trainings";
    }



    @GetMapping("/groups")
    public String getPageGroups(Model model, @AuthenticationPrincipal CustomUserDetails userDetails ) {
        User user = userDetails.getUser();
        if (user instanceof Coach coach) {
            List<GroupSportsman> groups = groupSportsmanService.findByCoach(coach);
            model.addAttribute("groups", groups);
        }
        return "coach/groups-coach";
    }

    @DeleteMapping("/groups")
    public String deleteGroups(@ModelAttribute GroupDeleteDto dto,
                               RedirectAttributes redirectAttributes) {
        if (dto.getGroupId() != null) {
            groupSportsmanService.deleteGroupById(dto.getGroupId());
            redirectAttributes.addFlashAttribute("success", "Группа удалена");
        }
        else {
            redirectAttributes.addFlashAttribute("error", "Не удалось удалить группу");
        }
        return "redirect:/electron/coach/groups";
    }

    @GetMapping("/groups/add")
    public String getPageAddGroupForm(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userDetails.getUser();
        if (user instanceof Coach coach) {
            List<Sportsman> athletes = sportsmanService.getAllSportsmenByCoach(coach);
            model.addAttribute("athletes", athletes);
        }
        return "coach/add-group";
    }

    @PostMapping("/groups/add")
    public String addGroup(@ModelAttribute GroupCreateDto dto,
                           @AuthenticationPrincipal CustomUserDetails userDetails,
                           RedirectAttributes redirectAttributes) {
        User user = userDetails.getUser();
        if (user instanceof Coach coach) {
            GroupSportsman group = groupSportsmanService.createGroup(dto.getGroupName(), coach, dto.getAthleteIds());
            redirectAttributes.addFlashAttribute("success", "Группа создана: " + group.getGroupName());
        }
        return "redirect:/electron/coach/groups";
    }

    @GetMapping("/groups/add-sportsman")
    public String showAddAthletesToGroupForm(@RequestParam Long groupId,
                                             Model model,
                                             @AuthenticationPrincipal CustomUserDetails userDetails,
                                             RedirectAttributes redirectAttributes) {
        User user = userDetails.getUser();
        if (user instanceof Coach coach) {
            GroupSportsman group = groupSportsmanService.findById(groupId);

            if (!group.getCoach().getId().equals(coach.getId()) ){
                redirectAttributes.addFlashAttribute("error","Это группа спортсменов вам недоступна, вы не являетесь у неё тренером");
                return "redirect:/electron/coach/groups";
            }
            List<Sportsman> allAthletes = sportsmanService.getAllSportsmenByCoach(coach);
            List<Sportsman> currentAthletes = group.getSportsmen();

            allAthletes.removeAll(currentAthletes);

            model.addAttribute("group", group);
            model.addAttribute("athletes", allAthletes);
        }
        return "coach/add-sportsman";
    }
    @PostMapping("/groups/add-sportsman")
    public String addSportsmanInGroups(@ModelAttribute SportsmanGroupUpdateDto dto,
                                       @AuthenticationPrincipal CustomUserDetails userDetails,
                                       RedirectAttributes redirectAttributes) {
        User user = userDetails.getUser();
        if (user instanceof Coach ) {
            if (dto.getGroupId() != null && dto.getAthleteIds() != null) {
                groupSportsmanService.addAthletesToGroup(dto.getGroupId(), dto.getAthleteIds());
                redirectAttributes.addFlashAttribute("success", "Спортсмены добавлены в группу");
                return "redirect:/electron/coach/groups";
            }
            else {
                redirectAttributes.addFlashAttribute("error", "Ошибка при добавлении спортсменов");
            }
        }
        return "redirect:/electron/coach/groups";
    }


    @DeleteMapping("/groups/delete-sportsman")
    public String deleteSportsmanFromGroups(@ModelAttribute SportsmanGroupUpdateDto dto,
                                            RedirectAttributes redirectAttributes) {
        if (dto.getSportsmanId() != null && dto.getGroupId() != null) {
            groupSportsmanService.removeAthleteFromGroup(dto.getGroupId(), dto.getSportsmanId());
            redirectAttributes.addFlashAttribute("success", "Спортсмен удалён из группы");
        }
        else {
            redirectAttributes.addFlashAttribute("error", "Не удалось удалить спортсмена из группы");
        }
        return "redirect:/electron/coach/groups";
    }
}
