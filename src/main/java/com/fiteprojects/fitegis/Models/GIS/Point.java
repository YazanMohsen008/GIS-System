package com.fiteprojects.fitegis.Models.GIS;


import java.io.Serializable;
import java.util.ArrayList;

public class Point extends Geometry implements Serializable {
    ArrayList<Double>coordinates;

    public Point() {
    }

    public Point(String type, ArrayList<Double> coordinates) {
        this.type = type;
        this.coordinates = coordinates;
    }


    public ArrayList<Double> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(ArrayList<Double> coordinates) {
        this.coordinates = coordinates;
    }

}
