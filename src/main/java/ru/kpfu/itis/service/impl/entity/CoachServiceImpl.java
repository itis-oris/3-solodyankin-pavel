package ru.kpfu.itis.service.impl.entity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.exception.CoachNotFoundException;
import ru.kpfu.itis.exception.SportsmanNotFoundException;
import ru.kpfu.itis.model.Coach;
import ru.kpfu.itis.model.GroupSportsman;
import ru.kpfu.itis.model.ScheduleTraining;
import ru.kpfu.itis.model.Sportsman;
import ru.kpfu.itis.repository.CoachRepository;
import ru.kpfu.itis.repository.GroupSportsmanRepository;
import ru.kpfu.itis.repository.ScheduleTrainingRepository;
import ru.kpfu.itis.repository.SportsmanRepository;
import ru.kpfu.itis.service.interfaces.CoachService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CoachServiceImpl implements CoachService {

    private final CoachRepository coachRepository;
    private final SportsmanRepository sportsmanRepository;
    private final GroupSportsmanRepository groupRepository;
    private final ScheduleTrainingRepository trainingRepository;

    @Override
    public void updateCoach(Coach coach) {
        coachRepository.save(coach);
    }

    @Override
    public Set<Coach> findAll() {
        return coachRepository.findAllWithSport();
    }

    @Override
    public List<Coach> findAllNotInCoaches(Sportsman sportsman) {
        return coachRepository.findAllNotInCoachesBySport(sportsman.getId());
    }

    @Override
    public void addCoachesToSportsman(Long sportsmanId, List<Long> coachIds) {
        Sportsman sportsman = sportsmanRepository.findByIdWithCoaches(sportsmanId)
                .orElseThrow(() -> new SportsmanNotFoundException("Спортсмен не найден с id: %s".formatted(sportsmanId)));

        for (Long coachId : coachIds) {
            Coach coach = coachRepository.findById(coachId)
                    .orElseThrow(() -> new CoachNotFoundException("Тренер не найден с id: %s ".formatted(coachId)));

            if (coach.getSportsmen() == null) {
                coach.setSportsmen(new ArrayList<>());
            }

            coach.getSportsmen().add(sportsman);
            coachRepository.save(coach);
        }

        sportsmanRepository.save(sportsman);
    }

    @Override
    public void removeCoachesFromSportsman(Long sportsmanId, List<Long> coachIds) {
        Sportsman sportsman = sportsmanRepository.findById(sportsmanId)
                .orElseThrow(() -> new SportsmanNotFoundException("Спортсмен не найден с id: %s".formatted(sportsmanId)));

        for (Long coachId : coachIds) {
            Coach coach = coachRepository.findById(coachId)
                    .orElseThrow(() -> new CoachNotFoundException("Тренер не найден с id: %s ".formatted(coachId)));

            if (coach.getSportsmen() != null) {
                coach.getSportsmen().removeIf(s -> s.getId().equals(sportsmanId));
                coachRepository.save(coach);
            }

            List<GroupSportsman> groups = groupRepository.findByCoachAndSportsmenContaining(coach, sportsman);

            for (GroupSportsman group : groups) {
                group.getSportsmen().removeIf(s -> s.getId().equals(sportsmanId));
                groupRepository.save(group);

                List<ScheduleTraining> trainingsToRemove = group.getTrainings().stream()
                        .filter(t -> t.getCoach().getId().equals(coachId))
                        .toList();

                for (ScheduleTraining training : trainingsToRemove) {
                    training.getGroupSportsmen().removeIf(g -> g.getId().equals(group.getId()));
                    trainingRepository.save(training);
                }
            }

            if (sportsman.getCoaches() != null) {
                sportsman.getCoaches().removeIf(c -> c.getId().equals(coachId));
                sportsmanRepository.save(sportsman);
            }
        }
    }
}