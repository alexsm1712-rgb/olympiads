package ru.olympinfo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import ru.olympinfo.service.OlympiadService;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final OlympiadService olympiadService;

    @GetMapping("/home")
    public String home(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        long totalOlympiads = olympiadService.getOlympiadCount();
        model.addAttribute("totalOlympiads", totalOlympiads);
        return "home";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }
}