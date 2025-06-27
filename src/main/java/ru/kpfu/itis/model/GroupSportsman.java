package ru.kpfu.itis.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "group_sportsman")
public class GroupSportsman {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "group_name")
    private String groupName;

    @ManyToOne
    @JoinColumn(name = "coach_id")
    private Coach coach;

    @ManyToMany
    @JoinTable(
            name = "group_sportsman_schedule_training",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "training_id")
    )
    private List<ScheduleTraining> trainings;

    @ManyToMany
    @JoinTable(
            name = "group_sportsman_user",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<Sportsman> sportsmen;
}

