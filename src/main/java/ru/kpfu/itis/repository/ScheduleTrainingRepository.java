package ru.kpfu.itis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.model.GroupSportsman;
import ru.kpfu.itis.model.ScheduleTraining;

import java.util.List;

public interface ScheduleTrainingRepository extends JpaRepository<ScheduleTraining, Long> {
    boolean existsByDayOfWeekAndTimeAndGroupSportsmenId(String day, String time, Long groupId);
}