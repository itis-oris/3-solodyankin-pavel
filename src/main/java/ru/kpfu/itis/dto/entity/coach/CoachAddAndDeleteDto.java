package ru.kpfu.itis.dto.entity.coach;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CoachAddAndDeleteDto {
    private Long sportsmanId;
    private List<Long> coachIds;
}
