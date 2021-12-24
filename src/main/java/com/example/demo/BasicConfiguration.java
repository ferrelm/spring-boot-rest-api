package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("basic")
public class BasicConfiguration {
    private int number;
    private boolean value;
    private String message;

    public int getNumber() {
        return number;
    }

    public boolean isValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Object> getAll() {
        List<Object> list = new ArrayList<>();

        list.add(number);
        list.add(value);
        list.add(message);

        return list;
    }
}
