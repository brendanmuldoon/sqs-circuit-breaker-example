package com.sqs.sqsexample.handlers;

import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler(RequestNotPermitted.class)
    public ResponseEntity<Object> handleRequestNotPermitted(RequestNotPermitted ex, HttpServletRequest request) {
        log.warn("Request to path '{}' is blocked due to rate-limiting. {}",
                request.getRequestURI(), ex.getMessage());

        return new ResponseEntity<>("Too many requests", HttpStatus.TOO_MANY_REQUESTS);

    }
}
