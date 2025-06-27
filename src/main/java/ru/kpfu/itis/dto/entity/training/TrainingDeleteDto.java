package ru.kpfu.itis.dto.entity.training;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TrainingDeleteDto {
    private Long trainingId;
}