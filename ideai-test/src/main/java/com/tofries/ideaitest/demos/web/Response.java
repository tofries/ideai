package com.tofries.ideaitest.demos.web;


import reactor.core.publisher.Flux;

public class Response {
    boolean code;
    Flux<String> stream;

    public Response(boolean code, Flux<String> stream) {
        this.code = code;
        this.stream = stream;
    }

    public boolean isCode() {
        return code;
    }

    public void setCode(boolean code) {
        this.code = code;
    }

    public Flux<String> getStream() {
        return stream;
    }

    public void setStream(Flux<String> stream) {
        this.stream = stream;
    }
}
