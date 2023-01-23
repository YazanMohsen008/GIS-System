package com.fiteprojects.fitegis.Models.GIS;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Feature implements Serializable {
    String type;
    String id;
    Geometry geometry;
    String geometry_name;
    HashMap<String, Serializable> properties = new HashMap<>();
    ;

    public Feature() {
    }

    public Feature(String type, String id, Geometry geometry, String geometry_name, HashMap<String, Serializable> properties) {
        this.type = type;
        this.id = id;
        this.geometry = geometry;
        this.geometry_name = geometry_name;
        this.properties = properties;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public String getGeometry_name() {
        return geometry_name;
    }

    public void setGeometry_name(String geometry_name) {
        this.geometry_name = geometry_name;
    }

    public HashMap<String, Serializable> getProperties() {
        return properties;
    }

    public void setProperties(HashMap<String, Serializable> properties) {
        this.properties = properties;
    }

    public void addProperty(String key, Serializable value) {
        properties.put(key, value);
    }
}
