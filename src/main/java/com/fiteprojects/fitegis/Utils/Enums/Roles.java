package com.fiteprojects.fitegis.Utils.Enums;

public enum Roles {

    SYSTEM_MANAGER_EN("system_manager"),
    SYSTEM_MANAGER_AR("مدير_النظام"),
    SHIFT_MANAGER_EN("shift_manager"),
    SHIFT_MANAGER_AR("مدير_الدوام"),
    TOOLS_MANAGER_EN("tools_manager"),
    TOOLS_MANAGER_AR("مدير_التجهيزات"),
    ;

    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    Roles(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return key;
    }
}
