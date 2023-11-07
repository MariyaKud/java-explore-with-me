package ru.practicum.explorewithme.exeption;

public class NotMeetRulesException extends RuntimeException {

    public NotMeetRulesException(String rules) {
       super(rules);
    }
}
