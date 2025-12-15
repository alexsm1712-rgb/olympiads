package ru.olympinfo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.olympinfo.model.Olympiad;
import ru.olympinfo.model.OlympiadStats;
import ru.olympinfo.model.User;
import ru.olympinfo.repository.OlympiadRepository;
import ru.olympinfo.repository.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OlympiadService {

    private final OlympiadRepository olympiadRepository;
    private final UserRepository userRepository;

    // Получить общее количество олимпиад
    public long getOlympiadCount() {
        return olympiadRepository.count();
    }

    public List<Olympiad> findAll() {
        return olympiadRepository.findAll();
    }

    public Olympiad findById(Long id) {
        return olympiadRepository.findById(id).orElse(null);
    }

    public void save(Olympiad olympiad) {
        olympiadRepository.save(olympiad);
    }

    public void delete(Long id) {
        olympiadRepository.deleteById(id);
    }

    public List<Olympiad> search(String query) {
        return olympiadRepository.findAll().stream()
                .filter(o -> o.getTitle().toLowerCase().contains(query.toLowerCase())
                        || o.getSubject().toLowerCase().contains(query.toLowerCase())
                        || o.getLevel().toLowerCase().contains(query.toLowerCase())
                        || o.getOrganizer().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Olympiad> searchInList(Collection<Olympiad> list, String query) {
        if (query == null || query.isEmpty()) return new ArrayList<>(list);
        return list.stream()
                .filter(o -> o.getTitle().toLowerCase().contains(query.toLowerCase())
                        || o.getSubject().toLowerCase().contains(query.toLowerCase())
                        || o.getLevel().toLowerCase().contains(query.toLowerCase())
                        || o.getOrganizer().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Olympiad> sortList(List<Olympiad> list, String sort) {
        if (sort == null || sort.isEmpty()) return list;
        Comparator<Olympiad> comparator = switch (sort) {
            case "title" -> Comparator.comparing(Olympiad::getTitle, String.CASE_INSENSITIVE_ORDER);
            case "subject" -> Comparator.comparing(Olympiad::getSubject, String.CASE_INSENSITIVE_ORDER);
            case "level" -> Comparator.comparing(Olympiad::getLevel, String.CASE_INSENSITIVE_ORDER);
            case "startDate" -> Comparator.comparing(Olympiad::getStartDate);
            case "endDate" -> Comparator.comparing(Olympiad::getEndDate);
            case "organizer" -> Comparator.comparing(Olympiad::getOrganizer, String.CASE_INSENSITIVE_ORDER);
            default -> null;
        };
        if (comparator != null) list.sort(comparator);
        return list;
    }

    public void toggleFavorite(Long olympiadId, User user) {
        Olympiad olympiad = findById(olympiadId);
        if (olympiad == null) return;
        if (user.getFavoriteOlympiads().contains(olympiad)) {
            user.getFavoriteOlympiads().remove(olympiad);
        } else {
            user.getFavoriteOlympiads().add(olympiad);
        }
        userRepository.save(user);
    }

    public Map<Long, Integer> countFavorites(Collection<Olympiad> list) {
        return list.stream().collect(Collectors.toMap(
                Olympiad::getId,
                o -> o.getUsers().size()
        ));
    }

    public boolean validateDates(Olympiad o) {
        if (o.getStartDate() == null || o.getEndDate() == null) return true;
        return !o.getStartDate().isAfter(o.getEndDate());
    }

    public List<OlympiadStats> getStats() {
        return olympiadRepository.findAll().stream()
                .map(o -> new OlympiadStats(o.getTitle(), o.getUsers().size()))
                .collect(Collectors.toList());
    }
}