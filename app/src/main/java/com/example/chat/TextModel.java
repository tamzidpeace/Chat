package com.example.chat;

public class TextModel {
    private String message;
    private String from;

    public TextModel() {
    }

    TextModel(String message, String from) {
        this.message = message;
        this.from = from;
    }

    String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFrom() {
        return from;
    }
}
