package ru.kpfu.itis.dto.entity.sportsman;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SportsmanGroupUpdateDto {
    private Long groupId;
    private List<Long> athleteIds;
    private Long sportsmanId;
}