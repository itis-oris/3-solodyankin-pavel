package ru.kpfu.itis.controller.entity;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.dto.response.UserResponse;
import ru.kpfu.itis.model.Coach;
import ru.kpfu.itis.model.Sportsman;
import ru.kpfu.itis.service.interfaces.CoachService;

import ru.kpfu.itis.service.interfaces.SportsmanService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/electron/user")
@RequiredArgsConstructor
public class UserController {

    private final CoachService coachService;
    private final SportsmanService sportsmanService;

    @GetMapping("/coaches")
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponse> getAllCoaches() {
        Set<Coach> coaches = coachService.findAll();
        return coaches.stream()
                .map(c -> new UserResponse(
                        c.getName(),
                        c.getAge(),
                        c.getPhone(),
                        c.getRank() != null ? c.getRank() : "",
                        c.getSport().getName()
                ))
                .toList();
    }

    @GetMapping("/sportsmen")
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponse> getAllSportsmen() {
        Set<Sportsman> sportsmen = sportsmanService.findAll();
        return sportsmen.stream()
                .map(s -> new UserResponse(
                        s.getName(),
                        s.getAge(),
                        s.getPhone(),
                        s.getRank() != null ? s.getRank() : "",
                        s.getCoaches().stream()
                                .map(c -> c.getSport().getName())
                                .collect(Collectors.joining(", "))

                ))
                .toList();
    }

}