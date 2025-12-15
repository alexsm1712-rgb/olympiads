package ru.olympinfo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OlympiadStats {
    private String title;
    private int favoriteCount;
}