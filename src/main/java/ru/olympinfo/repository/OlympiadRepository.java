package ru.olympinfo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.olympinfo.model.Olympiad;
import java.util.List;

public interface OlympiadRepository extends JpaRepository<Olympiad, Long> {
    List<Olympiad> findByTitleContainingIgnoreCase(String title);
    List<Olympiad> findByOrganizer(String organizer);
}