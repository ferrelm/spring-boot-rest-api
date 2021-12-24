package com.example.demo;

import java.time.Instant;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

@Service
@RequestScope
public class DisposableService {

    public String execute() {
        return "I am disposable";
    }

    @PostConstruct
    private void init() {
        System.out.println("NewDisposableService created at " + Instant.now().toEpochMilli());
    }

}
