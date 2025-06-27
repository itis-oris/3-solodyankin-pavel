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
public class Coach extends User {

    @Column(name = "rank")
    private String rank;

    @ManyToOne
    @JoinColumn(name = "sport_id")
    private Sport sport;

    @OneToMany(mappedBy = "coach", cascade = CascadeType.ALL)
    private List<GroupSportsman> groupSportsmen;

    @OneToMany(mappedBy = "coach", cascade = CascadeType.ALL)
    private List<ScheduleTraining> scheduleTrainings;

    @ManyToMany
    @JoinTable(
            name = "sportsman_coach",
            joinColumns = @JoinColumn(name = "coach_id"),
            inverseJoinColumns = @JoinColumn(name = "sportsman_id")
    )
    private List<Sportsman> sportsmen;
}

