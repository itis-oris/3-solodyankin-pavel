package ru.kpfu.itis.service.interfaces;

import ru.kpfu.itis.dto.entity.group.GroupWithTrainingsDto;
import ru.kpfu.itis.model.Coach;

import java.util.List;

public interface ScheduleTrainingService {
    List<GroupWithTrainingsDto> getTrainingsByCoachGroups(Coach coach);
    void createTraining(String day, String time, Long groupId);
    void deleteTraining(Long trainingId);

    boolean existsByDayAndTimeAndGroup(String day, String time, Long groupId);

}