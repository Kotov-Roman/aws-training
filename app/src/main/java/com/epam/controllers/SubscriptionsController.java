package com.epam.controllers;

import com.epam.service.impl.SnsSubscriptionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/subscriptions")
@RequiredArgsConstructor
public class SubscriptionsController {

    private final SnsSubscriptionsService snsSubscriptionsService;

    @PostMapping()
    public ResponseEntity<String> subscribe(@RequestParam("email") String email) {
        String result = snsSubscriptionsService.subscribe(email);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping()
    public ResponseEntity<Boolean> unsubscribe(@RequestParam("arn") String subscriptionArn) {
        boolean isDeleted = snsSubscriptionsService.unsubscribe(subscriptionArn);
        return ResponseEntity.ok().body(isDeleted);
    }
}
