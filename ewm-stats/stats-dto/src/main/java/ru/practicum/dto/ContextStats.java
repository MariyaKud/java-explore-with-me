package ru.practicum.dto;

import java.time.format.DateTimeFormatter;

public class ContextStats {
    public static final String pattern = "yyyy-MM-dd HH:mm:ss";
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
}
