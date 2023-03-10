package com.fiteprojects.fitegis.Utils.Enums;

public enum Response {

    Success("Success"),
    failed("Failed");

    private String message;

    Response(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
