package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 */
@RestController
@RequestMapping("api/v1/public")
public class MainController {

    @Autowired
    private BasicConfiguration basicConfiguration;

    @Autowired
    private DisposableService disposableService;

    @GetMapping("/configuration")
    public List<Object> configuration() {
        return basicConfiguration.getAll();
    }

    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    @GetMapping("/disposable")
    public String disposable() {
        return disposableService.execute();
    }

}
