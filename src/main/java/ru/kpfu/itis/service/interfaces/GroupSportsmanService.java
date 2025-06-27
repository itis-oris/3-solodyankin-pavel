package ru.kpfu.itis.service.interfaces;

import ru.kpfu.itis.model.Coach;
import ru.kpfu.itis.model.GroupSportsman;
import ru.kpfu.itis.model.Sportsman;

import java.util.List;

public interface GroupSportsmanService {
    GroupSportsman createGroup(String groupName, Coach coach, List<Long> athleteIds);
    void addAthletesToGroup(Long groupId, List<Long> athleteIds);
    void removeAthleteFromGroup(Long groupId, Long sportsmanId);
    void deleteGroupById(Long id);
    GroupSportsman findById(Long id);
    List<GroupSportsman> findByCoach(Coach coach);

    List<GroupSportsman> findBySportsman(Sportsman sportsman);
}