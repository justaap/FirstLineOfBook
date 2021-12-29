package com.example.uipractice;

public class Msg {
    public static final int TYEP_RECEIVED = 0;
    public static final int TYEP_SEND = 1;

    private String content;
    private int type;

    public Msg(String content, int type) {
        this.content = content;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public int getType() {
        return type;
    }
}
