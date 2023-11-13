package ru.practicum.exeption;

import lombok.extern.slf4j.Slf4j;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.UnexpectedTypeException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;

import static ru.practicum.dto.ContextStats.formatter;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler({NotFoundException.class, NotFoundListException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFoundException(final RuntimeException e) {
        log.debug("Got status 404 Not found {}", e.getMessage(), e);
        return getApiError(e, HttpStatus.NOT_FOUND.toString());
    }

    @ExceptionHandler({IllegalStateException.class,
            IllegalArgumentException.class,
            MethodArgumentTypeMismatchException.class,
            MethodArgumentNotValidException.class,
            MissingRequestHeaderException.class,
            MissingServletRequestParameterException.class,
            UnexpectedTypeException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleBadRequest(final Throwable e) {
        log.error("Validation error {}", e.getMessage(), e);
        return getApiError(e, HttpStatus.BAD_REQUEST.toString());
    }

    @ExceptionHandler({NotMeetRulesException.class,
            DataIntegrityViolationException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleConflict(final RuntimeException e) {
        log.error("409 {}", e.getMessage(), e);
        return getApiError(e, HttpStatus.CONFLICT.toString());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleThrowable(final Throwable e) {
        log.debug("Got status 500 {}", e.getMessage(), e);
        return getApiError(e, HttpStatus.INTERNAL_SERVER_ERROR.toString());
    }

    private String getStackTrace(Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);

        return sw.toString();
    }

    private ApiError getApiError(Throwable e, String stat) {
        return ApiError.builder()
                .message(e.getMessage())
                .timestamp(formatter.format(LocalDateTime.now()))
                .errors(getStackTrace(e))
                .status(stat)
                .reason(String.valueOf(e.getCause()))
                .build();
    }
}
