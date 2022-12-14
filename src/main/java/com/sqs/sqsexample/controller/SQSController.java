package com.sqs.sqsexample.controller;

import com.sqs.sqsexample.service.SQSService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class SQSController {

    private SQSService service;

    @RateLimiter(name = "backendA")
    @PostMapping("/api/send")
    public ResponseEntity<String> send(@RequestBody String message) {
        service.sendMessageToQueue(message);
        return new ResponseEntity<>("Message sent", HttpStatus.OK);
    }

}
