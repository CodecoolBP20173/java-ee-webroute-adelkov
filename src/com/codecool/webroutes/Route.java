package com.codecool.webroutes;


import java.io.PipedOutputStream;

public class Route {

    @WebRoute(path = "/test", method = Method.GET)
    public String onTest() {
        return "Test 1";
    }

    @WebRoute(path = "/index", method = Method.DELETE)
    public String onIndex() {
        return "index pblabla";

    }

    @WebRoute(path = "/practice", method = Method.POST)
    public String onPractice() {
        return "practice blabla";

    }

    @WebRoute(path = "/user/<userName>", method = Method.PUT)
    public String onUsername() {
        return "practice blabla";
    }
}
