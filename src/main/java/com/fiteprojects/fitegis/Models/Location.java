package com.fiteprojects.fitegis.Models;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fiteprojects.fitegis.Models.GIS.GISCoordinates;
import com.fiteprojects.fitegis.Models.GIS.PolygonToJsonSerializer;
import com.vividsolutions.jts.geom.Geometry;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;

@Entity
@Table(name = "hall", schema = "fite_geometry", catalog = "postgres")
public class Location extends GenericModel implements   GISCoordinates {

    @Column(name = "floor")
    Integer floor;
    @Column(name = "capacity")
    Integer capacity;
    @Column(name = "english_name")
    String en_name;
    @Column(name = "arabic_name")
    String ar_name;

    @Column(name = "type")
    String type;
    @Column(name = "english_description")
    String en_description;


    @Column(name = "arabic_description")
    String ar_description;
    @JsonSerialize(using = PolygonToJsonSerializer.class)
    @Column(name = "geom", columnDefinition = "Geometry")
    Geometry geom;
    @Column(name = "is_lecture_holder")
    Boolean isLectureHolder;
    @Transient
    ArrayList<ArrayList<Double>> coordinates;


    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getEn_name() {
        return en_name;
    }

    public void setEn_name(String en_name) {
        this.en_name = en_name;
    }

    public String getAr_name() {
        return ar_name;
    }

    public void setAr_name(String ar_name) {
        this.ar_name = ar_name;
    }

    public String getEn_description() {
        return en_description;
    }

    public void setEn_description(String en_description) {
        this.en_description = en_description;
    }

    public String getAr_description() {
        return ar_description;
    }

    public void setAr_description(String ar_description) {
        this.ar_description = ar_description;
    }
    @Override
    public Geometry getGeom() {
        return geom;
    }

    public void setGeom(Geometry geom) {
        this.geom = geom;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getLectureHolder() {
        return isLectureHolder;
    }

    public void setLectureHolder(Boolean lectureHolder) {
        isLectureHolder = lectureHolder;
    }

    public ArrayList<ArrayList<Double>> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(ArrayList<ArrayList<Double>> coordinates) {
        this.coordinates = coordinates;
    }

    public String getName(String lang) {
        if (lang.equals("ar"))
            return ar_name;
        else
            return en_name;
    }


    public String getDescription(String lang) {
        if (lang.equals("ar"))
            return ar_description;
        else
            return en_description;
    }
}
