package com.fiteprojects.fitegis.Utils.Enums;

public enum keys {

    FLOOR("floor"),
    ID("id"),
    EQUALS("="),
    AND("and")
    ;

    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    keys(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return key;
    }
}
