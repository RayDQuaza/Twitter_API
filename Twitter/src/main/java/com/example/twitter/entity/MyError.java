package com.example.twitter.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;


public class MyError {
    @JsonProperty("Error")
    private String m;

    @JsonIgnore
    public MyError(String Error) {
        this.m = Error;
    }

    @JsonIgnore
    public String getError() {
        return m;
    }

    @JsonIgnore
    public void setError(String Error) {
        this.m = Error;
    }
}
