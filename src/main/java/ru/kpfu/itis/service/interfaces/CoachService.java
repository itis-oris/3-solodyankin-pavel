package ru.kpfu.itis.service.interfaces;

import ru.kpfu.itis.model.Coach;
import ru.kpfu.itis.model.Sportsman;

import java.util.List;
import java.util.Set;

public interface CoachService {
    void updateCoach(Coach coach);
    Set<Coach> findAll();
    List<Coach> findAllNotInCoaches(Sportsman sportsman);
    void addCoachesToSportsman(Long sportsmanId, List<Long> coachIds);
    void removeCoachesFromSportsman(Long sportsmanId, List<Long> coachIds);

}
