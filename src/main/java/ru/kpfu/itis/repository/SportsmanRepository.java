package ru.kpfu.itis.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.kpfu.itis.model.Coach;
import ru.kpfu.itis.model.Sportsman;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface SportsmanRepository extends JpaRepository<Sportsman, Long> {
    List<Sportsman> findByCoachesContaining(Coach coach);

    @Query("SELECT s FROM Sportsman s LEFT JOIN FETCH s.coaches WHERE s.id = :id")
    Optional<Sportsman> findByIdWithCoaches(@Param("id") Long id);

    @Query("SELECT s FROM Sportsman s LEFT JOIN FETCH s.coaches c WHERE c.sport IS NOT NULL")
    Set<Sportsman> findAllWithCoachesAndSports();

}