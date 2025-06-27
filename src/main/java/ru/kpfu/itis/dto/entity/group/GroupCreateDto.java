package ru.kpfu.itis.dto.entity.group;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class GroupCreateDto {
    private String groupName;
    private List<Long> athleteIds;
}