package ru.kpfu.itis.service.impl.entity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.exception.GroupSportsmanNotFoundException;
import ru.kpfu.itis.model.*;
import ru.kpfu.itis.repository.GroupSportsmanRepository;
import ru.kpfu.itis.repository.SportsmanRepository;
import ru.kpfu.itis.service.interfaces.GroupSportsmanService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupSportsmanServiceImpl implements GroupSportsmanService {

    private final GroupSportsmanRepository groupRepository;
    private final SportsmanRepository sportsmanRepository;

    @Override
    public GroupSportsman createGroup(String groupName, Coach coach, List<Long> athleteIds) {
        GroupSportsman group = new GroupSportsman();
        group.setGroupName(groupName);
        group.setCoach(coach);

        if (athleteIds != null && !athleteIds.isEmpty()) {
            List<Sportsman> athletes = sportsmanRepository.findAllById(athleteIds);
            group.setSportsmen(athletes);
        }

        return groupRepository.save(group);
    }

    @Override
    public void addAthletesToGroup(Long groupId, List<Long> athleteIds) {
        GroupSportsman group = groupRepository.findById(groupId).orElseThrow(
                () -> new GroupSportsmanNotFoundException("Группа спортсменов не найдена")
        );
        List<Sportsman> athletes = sportsmanRepository.findAllById(athleteIds);
        group.getSportsmen().addAll(athletes);
        groupRepository.save(group);
    }

    @Override
    public void removeAthleteFromGroup(Long groupId, Long sportsmanId) {
        GroupSportsman group = groupRepository.findById(groupId).orElseThrow(
                () -> new GroupSportsmanNotFoundException("Группа спортсменов не найдена")
        );
        group.getSportsmen().removeIf(s -> s.getId().equals(sportsmanId));
        groupRepository.save(group);
    }

    @Override
    public void deleteGroupById(Long id) {
        groupRepository.deleteById(id);
    }

    @Override
    public GroupSportsman findById(Long id) {
        return groupRepository.findById(id).orElseThrow(
                () -> new GroupSportsmanNotFoundException("Группа спортсменов у тренера не найдена")
        );
    }

    @Override
    public List<GroupSportsman> findByCoach(Coach coach) {
        return groupRepository.findByCoach(coach);
    }

    @Override
    public List<GroupSportsman> findBySportsman(Sportsman sportsman) {
        return groupRepository.findBySportsmenContaining(sportsman);
    }
}