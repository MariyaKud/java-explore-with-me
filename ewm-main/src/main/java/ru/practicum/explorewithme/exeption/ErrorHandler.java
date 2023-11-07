package ru.practicum.explorewithme.exeption;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;

import static ru.practicum.dto.ContextStats.formatter;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFoundException(final NotFoundException e) {
        log.debug("Got status 404 Not found {}", e.getMessage(), e);

        return new ApiError(getStackTrace(e), e.getMessage(), HttpStatus.NOT_FOUND.getReasonPhrase(),
                HttpStatus.NOT_FOUND.toString(), formatter.format(LocalDateTime.now()));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleIllegalStateException(final IllegalStateException e) {
        log.error("400 {}", e.getMessage(), e);

        return new ApiError(getStackTrace(e), e.getMessage(), HttpStatus.NOT_FOUND.getReasonPhrase(),
                HttpStatus.NOT_FOUND.toString(), formatter.format(LocalDateTime.now()));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleConflict(final NotMeetRulesException e) {
        log.error("409 {}", e.getMessage(), e);

        return new ApiError(getStackTrace(e), e.getMessage(), HttpStatus.NOT_FOUND.getReasonPhrase(),
                HttpStatus.NOT_FOUND.toString(), formatter.format(LocalDateTime.now()));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleThrowable(final Throwable e, HttpStatus status) {
        log.debug(String.format("Got status 404 %s",  status.value()), e.getMessage(), e);

        return new ApiError(getStackTrace(e), e.getMessage(), status.getReasonPhrase(),
                            status.toString(), formatter.format(LocalDateTime.now()));
    }

    private String getStackTrace(Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);

        return sw.toString();
    }
}
