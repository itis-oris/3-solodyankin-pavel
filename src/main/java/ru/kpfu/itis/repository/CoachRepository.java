package ru.kpfu.itis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.model.Coach;
import ru.kpfu.itis.model.Sportsman;

import java.util.List;
import java.util.Set;


@Repository
public interface CoachRepository extends JpaRepository<Coach, Long> {


    @Query("""
        SELECT c FROM Coach c 
        LEFT JOIN FETCH c.sport
        WHERE c NOT IN (
            SELECT s.coaches FROM Sportsman s WHERE s.id = :sportsmanId
        )
        AND c.sport NOT IN (
            SELECT coach.sport FROM Sportsman s JOIN s.coaches coach WHERE s.id = :sportsmanId
        )
        AND c.sport IS NOT NULL
        """)
    List<Coach> findAllNotInCoachesBySport(@Param("sportsmanId") Long sportsmanId);

    @Query("SELECT c FROM Coach c LEFT JOIN FETCH c.sport WHERE c.sport IS NOT NULL")
    Set<Coach> findAllWithSport();

}
