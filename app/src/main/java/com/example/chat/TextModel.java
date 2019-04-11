package com.example.chat;

public class TextModel {
    private String message;

    TextModel(String message) {
        this.message = message;
    }

    String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
