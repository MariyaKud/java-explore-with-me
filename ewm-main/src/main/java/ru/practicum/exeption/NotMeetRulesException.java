package ru.practicum.exeption;

public class NotMeetRulesException extends RuntimeException {

    public NotMeetRulesException(String rules) {
       super(rules);
    }
}
