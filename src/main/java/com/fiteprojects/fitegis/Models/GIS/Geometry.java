package com.fiteprojects.fitegis.Models.GIS;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Geometry implements Serializable {
    String type;

    public Geometry() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
