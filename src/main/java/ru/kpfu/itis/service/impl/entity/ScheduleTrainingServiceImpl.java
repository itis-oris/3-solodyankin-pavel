package ru.kpfu.itis.service.impl.entity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.dto.entity.group.GroupWithTrainingsDto;
import ru.kpfu.itis.exception.ScheduleTrainingNotFoundException;
import ru.kpfu.itis.model.Coach;
import ru.kpfu.itis.model.GroupSportsman;
import ru.kpfu.itis.model.ScheduleTraining;
import ru.kpfu.itis.repository.GroupSportsmanRepository;
import ru.kpfu.itis.repository.ScheduleTrainingRepository;
import ru.kpfu.itis.service.interfaces.ScheduleTrainingService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleTrainingServiceImpl implements ScheduleTrainingService {

    private final ScheduleTrainingRepository trainingRepository;
    private final GroupSportsmanRepository groupRepository;


    @Override
    public List<GroupWithTrainingsDto> getTrainingsByCoachGroups(Coach coach) {
        List<GroupSportsman> groups = groupRepository.findByCoach(coach);

        return groups.stream()
                .map(group -> new GroupWithTrainingsDto(
                        group,
                        group.getTrainings()))
                .collect(Collectors.toList());
    }

    @Override
    public void createTraining(String day, String time, Long groupId) {
        GroupSportsman group = groupRepository.findById(groupId).orElseThrow();

        ScheduleTraining training = new ScheduleTraining();
        training.setDayOfWeek(day);
        training.setTime(time);
        training.setCoach(group.getCoach());

        training = trainingRepository.save(training);

        group.getTrainings().add(training);
        groupRepository.save(group);

        groupRepository.save(group);
    }

    @Override
    public void deleteTraining(Long trainingId) {
        ScheduleTraining training = trainingRepository.findById(trainingId)
                .orElseThrow(() -> new ScheduleTrainingNotFoundException("Тренировка не найдена"));

        for (GroupSportsman group : training.getGroupSportsmen()) {
            group.getTrainings().remove(training);
            groupRepository.save(group);
        }

        trainingRepository.deleteById(trainingId);
    }

    @Override
    public boolean existsByDayAndTimeAndGroup(String day, String time, Long groupId) {
        return trainingRepository.existsByDayOfWeekAndTimeAndGroupSportsmenId(day, time, groupId);
    }
}