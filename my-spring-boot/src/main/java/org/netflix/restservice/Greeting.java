package org.netflix.restservice;

public record Greeting(long id, String content) {
    public Greeting(long id, String content) {
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
