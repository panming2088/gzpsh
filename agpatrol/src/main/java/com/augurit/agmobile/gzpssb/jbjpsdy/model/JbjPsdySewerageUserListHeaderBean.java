package com.augurit.agmobile.gzpssb.jbjpsdy.model;

/**
 * @PROJECT GZPSH
 * @USER Augurit
 * @CREATE 2021/3/26 11:47
 */
public class JbjPsdySewerageUserListHeaderBean {
    private final String text;
    private final String key;
    private final int value;

    public JbjPsdySewerageUserListHeaderBean(String text, String key, int value) {
        this.text = text;
        this.key = key;
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public String getKey() {
        return key;
    }

    public int getValue() {
        return value;
    }
}
