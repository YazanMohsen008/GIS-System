package com.fiteprojects.fitegis.Models.GIS;


import java.io.Serializable;
import java.util.ArrayList;

public class Polygon extends Geometry implements Serializable {
    ArrayList<ArrayList<ArrayList<Double>>> coordinates;

    public Polygon() {
    }

    public Polygon(String type, ArrayList<ArrayList<ArrayList<Double>>> coordinates) {
        this.type = type;
        this.coordinates = coordinates;
    }


    public ArrayList<ArrayList<ArrayList<Double>>> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(ArrayList<ArrayList<ArrayList<Double>>> coordinates) {
        this.coordinates = coordinates;
    }

    public void setCoordinatesList(ArrayList<ArrayList<Double>> coordinates) {
        this.coordinates = new ArrayList<>();
        this.coordinates.add(coordinates);
    }
}
