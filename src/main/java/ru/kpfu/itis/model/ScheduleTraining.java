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
@Table(name = "schedule_training")
public class ScheduleTraining {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "day_of_week")
    private String dayOfWeek;

    @Column(name = "time")
    private String time;

    @ManyToOne
    @JoinColumn(name = "coach_id")
    private Coach coach;

    @ManyToMany(mappedBy = "trainings")
    private List<GroupSportsman> groupSportsmen;
}

