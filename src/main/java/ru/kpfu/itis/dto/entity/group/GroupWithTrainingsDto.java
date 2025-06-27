package ru.kpfu.itis.dto.entity.group;

import lombok.*;
import ru.kpfu.itis.model.GroupSportsman;
import ru.kpfu.itis.model.ScheduleTraining;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class GroupWithTrainingsDto {
    private GroupSportsman groupSportsman;
    private List<ScheduleTraining> trainings;
}
