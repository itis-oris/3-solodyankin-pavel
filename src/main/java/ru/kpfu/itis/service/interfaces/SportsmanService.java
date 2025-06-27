package ru.kpfu.itis.service.interfaces;

import ru.kpfu.itis.model.Coach;
import ru.kpfu.itis.model.Sportsman;

import java.util.List;
import java.util.Set;

public interface SportsmanService {
    List<Sportsman> getAllSportsmenByCoach(Coach coach);

    void updateSportsman(Sportsman sportsman);

    Sportsman getSportsmanWithCoaches(Long id);

    Set<Sportsman> findAll();
}
