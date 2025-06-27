package ru.kpfu.itis.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@PrimaryKeyJoinColumn(name = "user_id")
public class Sportsman extends User {

    @Column(name = "rank")
    private String rank;

    @ManyToMany(mappedBy = "sportsmen")
    private List<GroupSportsman> groupSportsmen;

    @ManyToMany(mappedBy = "sportsmen")
    private List<Coach> coaches;
}

