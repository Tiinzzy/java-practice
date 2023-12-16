package org.netflix.rest_services;

public record RestAPI(long id, String content) {
    public RestAPI(long id, String content) {
        this.id = id;
        this.content = content;
    }

    @Override
    public long id() {
        return id;
    }

    @Override
    public String content() {
        return content.toUpperCase();
    }
}
