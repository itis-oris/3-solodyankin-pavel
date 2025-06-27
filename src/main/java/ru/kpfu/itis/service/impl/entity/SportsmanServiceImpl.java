package ru.kpfu.itis.service.impl.entity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.exception.SportsmanNotFoundException;
import ru.kpfu.itis.model.Coach;
import ru.kpfu.itis.model.Sportsman;
import ru.kpfu.itis.repository.SportsmanRepository;
import ru.kpfu.itis.service.interfaces.SportsmanService;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SportsmanServiceImpl implements SportsmanService {
    private final SportsmanRepository sportsmanRepository;

    @Override
    public List<Sportsman> getAllSportsmenByCoach(Coach coach) {
        return sportsmanRepository.findByCoachesContaining(coach);
    }


    @Override
    public void updateSportsman(Sportsman sportsman) {
        sportsmanRepository.save(sportsman);
    }


    @Override
    public Sportsman getSportsmanWithCoaches(Long id) {
        return sportsmanRepository.findByIdWithCoaches(id).orElseThrow(
                () -> new SportsmanNotFoundException("Спортсмен не найден")

        );
    }

    @Override
    public Set<Sportsman> findAll() {
        return sportsmanRepository.findAllWithCoachesAndSports();
    }
}
