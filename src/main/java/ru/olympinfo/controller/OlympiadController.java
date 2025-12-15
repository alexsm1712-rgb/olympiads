package ru.olympinfo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.olympinfo.model.Olympiad;
import ru.olympinfo.model.User;
import ru.olympinfo.service.OlympiadService;
import ru.olympinfo.service.UserService;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/olympiads")
public class OlympiadController {

    private final OlympiadService olympiadService;
    private final UserService userService;

    @GetMapping
    public String list(@RequestParam(required = false) String q,
                       @RequestParam(required = false) String sort,
                       Model model,
                       @AuthenticationPrincipal UserDetails userDetails) {

        List<Olympiad> olympiads = (q != null && !q.isEmpty()) ?
                olympiadService.search(q) : olympiadService.findAll();

        olympiads = olympiadService.sortList(olympiads, sort);

        Map<Long, Integer> favoritesCount = olympiadService.countFavorites(olympiads);

        User currentUser = userService.findByUsername(userDetails.getUsername());

        model.addAttribute("olympiads", olympiads);
        model.addAttribute("favoritesCount", favoritesCount);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("q", q);
        model.addAttribute("sort", sort);
        // Общее количество олимпиад
        long totalOlympiads = olympiadService.getOlympiadCount();
        model.addAttribute("totalOlympiads", totalOlympiads);

        return "olympiads";
    }

    @GetMapping("/favorites")
    public String favorites(@RequestParam(required = false) String q,
                            @RequestParam(required = false) String sort,
                            Model model,
                            @AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = userService.findByUsername(userDetails.getUsername());
        List<Olympiad> favorites = olympiadService.sortList(
                olympiadService.searchInList(currentUser.getFavoriteOlympiads(), q), sort);

        Map<Long, Integer> favoritesCount = olympiadService.countFavorites(favorites);

        model.addAttribute("olympiads", favorites);
        model.addAttribute("favoritesCount", favoritesCount);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("q", q);
        model.addAttribute("sort", sort);
        return "favorites";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("olympiad", new Olympiad());
        return "add-olympiad";
    }

    @PostMapping("/add")
    public String addOlympiad(@ModelAttribute Olympiad olympiad,
                              @AuthenticationPrincipal UserDetails userDetails,
                              Model model) {
        if (!olympiadService.validateDates(olympiad)) {
            model.addAttribute("error", "Дата начала не может быть после даты окончания!");
            model.addAttribute("olympiad", olympiad);
            return "add-olympiad";
        }
        olympiad.setOrganizer(userDetails.getUsername());
        olympiadService.save(olympiad);
        return "redirect:/olympiads";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Olympiad olympiad = olympiadService.findById(id);
        if (olympiad == null) return "redirect:/olympiads";
        model.addAttribute("olympiad", olympiad);
        return "edit-olympiad";
    }

    @PostMapping("/edit")
    public String editOlympiad(@ModelAttribute Olympiad olympiad, Model model) {
        if (!olympiadService.validateDates(olympiad)) {
            model.addAttribute("error", "Дата начала не может быть после даты окончания!");
            model.addAttribute("olympiad", olympiad);
            return "edit-olympiad";
        }
        olympiadService.save(olympiad);
        return "redirect:/olympiads";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        olympiadService.delete(id);
        return "redirect:/olympiads";
    }

    @PostMapping("/favorite/{id}")
    public String toggleFavorite(@PathVariable Long id,
                                 @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername());
        olympiadService.toggleFavorite(id, user);
        return "redirect:/olympiads";
    }

}