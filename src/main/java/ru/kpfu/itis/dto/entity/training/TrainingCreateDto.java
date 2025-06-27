package ru.kpfu.itis.dto.entity.training;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TrainingCreateDto {
    private String day;
    private String time;
    private List<Long> groupIds;
}