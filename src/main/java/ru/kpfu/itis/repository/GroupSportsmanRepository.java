package ru.kpfu.itis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.model.Coach;
import ru.kpfu.itis.model.GroupSportsman;
import ru.kpfu.itis.model.Sportsman;

import java.util.List;
import java.util.Optional;


public interface GroupSportsmanRepository extends JpaRepository<GroupSportsman, Long> {
    List<GroupSportsman> findByCoach(Coach coach);
    List<GroupSportsman> findBySportsmenContaining(Sportsman sportsman);
    List<GroupSportsman> findByCoachAndSportsmenContaining(Coach coach, Sportsman sportsman);
}