package com.fiteprojects.fitegis.Utils.Enums;

public enum ToolTypes {

    CAMERAS_EN("Cameras"),
    CAMERAS_AR("الكاميرات"),
    PROJECTORS_EN("Projectors"),
    PROJECTORS_AR("أجهزة الإسقاط"),
    ACTIVE_STATUS("active"),
    INACTIVE_STATUS("inActive");

    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    ToolTypes(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return key;
    }
}
